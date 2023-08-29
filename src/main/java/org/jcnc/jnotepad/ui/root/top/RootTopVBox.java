package org.jcnc.jnotepad.ui.root.top;

import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.root.top.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.ui.root.top.tools.ToolHBox;

public class RootTopVBox extends VBox {
    private static final RootTopVBox INSTANCE = new RootTopVBox();

    private RootTopVBox() {
        // 在构造函数中初始化
        getChildren().add(JNotepadMenuBar.getInstance());
        getChildren().add(ToolHBox.getInstance());
    }

    public static RootTopVBox getInstance() {
        return INSTANCE;
    }

}
