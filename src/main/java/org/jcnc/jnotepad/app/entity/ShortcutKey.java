package org.jcnc.jnotepad.app.entity;

/**
 * 快捷键
 *
 * @author gewuyou
 */
public class ShortcutKey {
    private String buttonName;
    private String shortcutKeyValue;

    public ShortcutKey() {
    }

    public ShortcutKey(String buttonName, String shortcutKeyValue) {
        this.buttonName = buttonName;
        this.shortcutKeyValue = shortcutKeyValue;
    }

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
