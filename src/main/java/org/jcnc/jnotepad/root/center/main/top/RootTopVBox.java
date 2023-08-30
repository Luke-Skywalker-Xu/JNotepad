package org.jcnc.jnotepad.root.center.main.top;

import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.root.center.main.top.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.root.center.main.top.tools.ToolHBox;

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
