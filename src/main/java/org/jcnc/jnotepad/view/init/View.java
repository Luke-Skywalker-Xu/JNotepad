package org.jcnc.jnotepad.view.init;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.controller.manager.ShortcutKey;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 许轲
 */
public class View {
    // todo 这个东西干嘛的，都没用过
    private static Map<String, Object> itmeMap = new HashMap<>();

    public void initItem() {
        ViewManager viewManager = ViewManager.getInstance();
        // 初始化菜单项的事件处理器
        EventHandler<ActionEvent> newFileEventHandler = Controller.getInstance().getNewFileEventHandler(new LineNumberTextArea());
        viewManager.getNewItem().setOnAction(newFileEventHandler);
        EventHandler<ActionEvent> openFileEventHandler = Controller.getInstance().getOpenFileEventHandler();
        viewManager.getOpenItem().setOnAction(openFileEventHandler);
        EventHandler<ActionEvent> saveAsFileEventHandler = Controller.getInstance().getSaveAsFileEventHandler();
        viewManager.getSaveAsItem().setOnAction(saveAsFileEventHandler);
        viewManager.getLineFeedItem().setOnAction(Controller.getInstance().getLineFeedEventHandler(new LineNumberTextArea()));
    }

    public void initTabPane() {
        Controller.getInstance().initTabPane();
    }

    // 初始化快捷键
    public void initShortcutKey() {
        new ShortcutKey().createShortcutKeyByConfig();
    }

}
