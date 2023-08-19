package org.jcnc.jnotepad.view.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.controller.manager.ShortcutKey;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.view.manager.ViewManager.*;


public class View {
        private static Map<String, Object> itmeMap = new HashMap<>();

    public void initItem() {
        // 初始化菜单项的事件处理器
        EventHandler<ActionEvent> newFileEventHandler = Controller.getInstance().getNewFileEventHandler(new LineNumberTextArea());
        newItem.setOnAction(newFileEventHandler);
        EventHandler<ActionEvent> openFileEventHandler = Controller.getInstance().getOpenFileEventHandler();
        openItem.setOnAction(openFileEventHandler);
        EventHandler<ActionEvent> saveAsFileEventHandler = Controller.getInstance().getSaveAsFileEventHandler();
        saveAsItem.setOnAction(saveAsFileEventHandler);
        lineFeedItem.setOnAction(Controller.getInstance().getLineFeedEventHandler(new LineNumberTextArea()));
    }

    public void initTabPane() {
        Controller.getInstance().initTabPane();
    }

    // 初始化快捷键
    public void initShortcutKey(){
        new ShortcutKey().createShortcutKeyByConfig();
    }

}
