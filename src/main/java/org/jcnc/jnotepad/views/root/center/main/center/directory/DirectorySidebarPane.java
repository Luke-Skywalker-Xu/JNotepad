package org.jcnc.jnotepad.views.root.center.main.center.directory;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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

    private DirectorySidebarPane() {
        this.setVisible(false);

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<DirFileModel> item = DirectorySidebarPane.this.getSelectionModel().getSelectedItem();
                    if (Objects.isNull(item)) return;
                    File file = new File(item.getValue().getPath());

                    if(!file.isFile()){
                        return;
                    }
                    new OpenFile().openFile(file);

                }
            }
        });
    }

    public static DirectorySidebarPane getInstance() {
        return INSTANCE;
    }


}
