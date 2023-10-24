package org.jcnc.jnotepad.ui.views.root.center.main.center.directory;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.jcnc.jnotepad.api.core.views.menu.builder.ContextMenuBuilder;
import org.jcnc.jnotepad.model.entity.DirFileModel;
import org.jcnc.jnotepad.ui.views.manager.DirectorySidebarManager;
import org.jcnc.jnotepad.ui.views.manager.MenuManager;

import java.io.File;
import java.util.Objects;

import static org.jcnc.jnotepad.app.common.constants.TextConstants.OPEN;
import static org.jcnc.jnotepad.app.utils.TabUtil.openFileToTab;


/**
 * 目录树ui
 *
 * <p>TreeView封装</p>
 *
 * @author cccqyu
 */
public class DirectorySidebarPane extends TreeView<DirFileModel> {

    private static final DirectorySidebarPane INSTANCE = new DirectorySidebarPane();

    private static final int CLICK_COUNT = 2;

    private final DirectorySidebarManager directorySidebarManager = DirectorySidebarManager.getInstance();

    private ContextMenu contextMenu;

    private DirectorySidebarPane() {
        initMouseClickEvent();
    }

    /**
     * Initializes the mouse click event for the Java function.
     */
    private void initMouseClickEvent() {
        this.setOnMouseClicked(mouseEvent -> {
            TreeItem<DirFileModel> item = DirectorySidebarPane.this.getSelectionModel().getSelectedItem();
            if (Objects.isNull(item)) {
                return;
            }
            if (mouseEvent.getClickCount() == CLICK_COUNT) {
                File file = new File(item.getValue().getPath());
                if (!file.isFile()) {
                    return;
                }
                openFileToTab(file);
            }
            if (!Objects.isNull(contextMenu)) {
                contextMenu.hide();
            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                updateContextMenu(mouseEvent, item);
            }
        });
    }


    private void updateContextMenu(MouseEvent mouseEvent, TreeItem<DirFileModel> item) {
        createContextMenu(item);
        contextMenu.show(this, mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    private void createContextMenu(TreeItem<DirFileModel> item) {
        ContextMenuBuilder builder = new ContextMenuBuilder();
        boolean isFile = !DirFileModel.isDirectoryByDirFileModel(item.getValue());
        File file = new File(item.getValue().getPath());
        contextMenu = builder
                .addMenuItem(OPEN, e -> openFileToTab(file), isFile)
                .addSeparatorMenuItem(isFile)
                .addMenu(MenuManager.getNewMenu(), !isFile)
//                .addSeparatorMenuItem()
//                .addMenuItem(RENAME, e -> directorySidebarManager.rename(file))
//                .addMenuItem(DELETE, e -> directorySidebarManager.delete(file))
//                .addSeparatorMenuItem(isFile)
                .addMenu(MenuManager.getCopyMenu(file), isFile)
                .addSeparatorMenuItem(isFile)
                .addMenu(MenuManager.getOpenMenu(file))
                .build();
    }


    public static DirectorySidebarPane getInstance() {
        return INSTANCE;
    }


}
