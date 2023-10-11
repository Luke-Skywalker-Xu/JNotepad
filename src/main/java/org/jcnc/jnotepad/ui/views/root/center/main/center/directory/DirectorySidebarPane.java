package org.jcnc.jnotepad.ui.views.root.center.main.center.directory;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jcnc.jnotepad.model.entity.DirFileModel;

import java.io.File;
import java.util.Objects;

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

    private DirectorySidebarPane() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == CLICK_COUNT) {
                TreeItem<DirFileModel> item = DirectorySidebarPane.this.getSelectionModel().getSelectedItem();
                if (Objects.isNull(item)) {
                    return;
                }
                File file = new File(item.getValue().getPath());

                if (!file.isFile()) {
                    return;
                }
                openFileToTab(file);
            }
        });
    }

    public static DirectorySidebarPane getInstance() {
        return INSTANCE;
    }


}
