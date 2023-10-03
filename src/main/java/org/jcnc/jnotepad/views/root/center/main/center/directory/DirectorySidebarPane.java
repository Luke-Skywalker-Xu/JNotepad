package org.jcnc.jnotepad.views.root.center.main.center.directory;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jcnc.jnotepad.controller.event.handler.menubar.OpenFile;
import org.jcnc.jnotepad.model.entity.DirFileModel;

import java.io.File;
import java.util.Objects;


/**
 * @author : cccqyu
 * @createTime 2023/10/2  20:34
 * @description TreeView封装
 */
public class DirectorySidebarPane extends TreeView<DirFileModel> {

    private static final DirectorySidebarPane INSTANCE = new DirectorySidebarPane();

    private static final int CLICK_COUNT = 2;

    private DirectorySidebarPane() {
        this.setVisible(false);

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
                OpenFile.openFile(file);

            }
        });
    }

    public static DirectorySidebarPane getInstance() {
        return INSTANCE;
    }


}
