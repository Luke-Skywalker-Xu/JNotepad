package org.jcnc.jnotepad.model;

/**
 * 快捷键信息类
 *
 * @author gewuyou
 */
public class ShortcutKey {
    private String buttonName;
    private String shortcutKeyValue;

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getShortcutKeyValue() {
        return shortcutKeyValue;
    }

    public void setShortcutKeyValue(String shortcutKeyValue) {
        this.shortcutKeyValue = shortcutKeyValue;
    }
}
