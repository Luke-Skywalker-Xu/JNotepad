package org.jcnc.jnotepad.cache.entity;


/**
 * 快捷键信息类
 *
 * @author gewuyou
 */
public class ShortcutKey {
    /**
     * 按钮名称
     */
    private String buttonName;
    /**
     * 快捷键值
     */
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
