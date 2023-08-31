package org.jcnc.jnotepad.root.left.sidebar.tools;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ToolHBox extends HBox {
    private static final ToolHBox INSTANCE = new ToolHBox();

    private ToolHBox() {

        HBox.setHgrow(JNotepadToolBar.getInstance(), Priority.ALWAYS); // 设置子节点水平拉伸

        getChildren().add(JNotepadToolBar.getInstance());
    }

    public static ToolHBox getInstance() {
        return INSTANCE;
    }


}
