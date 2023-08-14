package org.jcnc.jnotepad.view.init;

import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.controller.manager.Controller;

import static org.jcnc.jnotepad.view.manager.ViewManager.*;


public class View {

    public static void initItem() {
        // 初始化菜单项的事件处理器
        newItem.setOnAction(new Controller().getNewFileEventHandler(new TextArea()));
        openItem.setOnAction(new Controller().getOpenFileEventHandler());
        saveAsItem.setOnAction(new Controller().getSaveAsFileEventHandler());
        lineFeedItem.setOnAction(new Controller().getLineFeedEventHandler(new TextArea()));

    }

    public static void initTabPane() {
        new Controller().initTabPane();
    }
}
