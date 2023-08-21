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

    public void initTabPane() {
        Controller.getInstance().initTabPane();
    }

    // 初始化快捷键
    public void initShortcutKey() {
        new ShortcutKey().createShortcutKeyByConfig();
    }

}
