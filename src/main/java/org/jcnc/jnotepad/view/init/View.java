package org.jcnc.jnotepad.view.init;

import org.jcnc.jnotepad.controller.manager.ShortcutKey;


/**
 * @author 许轲
 */
public class View {

    // 初始化快捷键
    public void initShortcutKey() {
        new ShortcutKey().createShortcutKeyByConfig();
    }

}
