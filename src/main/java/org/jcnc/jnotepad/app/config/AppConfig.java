package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jcnc.jnotepad.model.entity.ShortcutKey;

import java.util.ArrayList;
import java.util.List;

import static org.jcnc.jnotepad.common.constants.TextConstants.CHINESE;

/**
 * 数据模型类，用于表示 MyData 对象的数据结构。
 *
 * @author 许轲
 */
public class AppConfig {
    private static final String CTRL_N = "ctrl+n";
    private static final String CTRL_O = "ctrl+o";
    private static final String CTRL_S = "ctrl+s";
    private static final String CTRL_ALT_S = "ctrl+alt+s";
    private static final String ALT_S = "alt+s";
    private String language;
    @JsonIgnore
    private boolean textWrap;
    private List<ShortcutKey> shortcutKey;

    /**
     * 生成默认应用配置对象。
     *
     * @return 默认应用配置对象
     */
    public static AppConfig generateDefaultAppConfig() {
        AppConfig myData = new AppConfig();
        myData.setLanguage(CHINESE);
        myData.setTextWrap(false);

        List<ShortcutKey> shortcutKeys = new ArrayList<>();
        shortcutKeys.add(createShortcutKey("newItem", CTRL_N));
        shortcutKeys.add(createShortcutKey("openItem", CTRL_O));
        shortcutKeys.add(createShortcutKey("saveItem", CTRL_S));
        shortcutKeys.add(createShortcutKey("saveAsItem", CTRL_ALT_S));
        shortcutKeys.add(createShortcutKey("lineFeedItem", ""));
        shortcutKeys.add(createShortcutKey("openConfigItem", ALT_S));
        shortcutKeys.add(createShortcutKey("pluginManager", ""));
        shortcutKeys.add(createShortcutKey("countItem", ""));
        myData.setShortcutKey(shortcutKeys);
        return myData;
    }

    /**
     * 创建 ShortcutKey 对象。
     *
     * @param buttonName       按钮名称
     * @param shortcutKeyValue 快捷键值
     * @return ShortcutKey 对象
     */
    private static ShortcutKey createShortcutKey(String buttonName, String shortcutKeyValue) {
        ShortcutKey shortcutKey = new ShortcutKey();
        shortcutKey.setButtonName(buttonName);
        shortcutKey.setShortcutKeyValue(shortcutKeyValue);
        return shortcutKey;
    }

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

    public boolean isTextWrap() {
        return textWrap;
    }

    public void setTextWrap(boolean textWrap) {
        this.textWrap = textWrap;
    }
}
