package org.jcnc.jnotepad.json;

import java.util.List;

/**
 * 数据模型类，用于表示 MyData 对象的数据结构。
 */
public class MyData {
    private String language;
    private List<ShortcutKey> shortcutKey;

    /**
     * ShortcutKey 类，用于表示快捷键信息。
     */
    public static class ShortcutKey {
        private String buttonName;
        private String shortcutKeyValue;

        public ShortcutKey() {}

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

    public MyData() {}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<ShortcutKey> getShortcutKey() {
        return shortcutKey;
    }

    public void setShortcutKey(List<ShortcutKey> shortcutKey) {
        this.shortcutKey = shortcutKey;
    }
}
