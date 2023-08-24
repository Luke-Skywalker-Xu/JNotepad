package org.jcnc.jnotepad.app.config;


import org.jcnc.jnotepad.app.entity.ShortcutKey;
import org.jcnc.jnotepad.app.entity.Style;

import java.util.List;

/**
 * 应用程序配置类
 *
 * @author gewuyou 一个大转盘
 * @see [相关类/方法]
 */
public class JnotepadConfig {
    /**
     * 快捷键列表
     */
    private List<ShortcutKey> shortcutKeyList;

    /**
     * 样式列表 TODO
     */
    private List<Style> styleList;

    /**
     * 单例模式
     */
    private JnotepadConfig() {
    }

    private static final JnotepadConfig INSTANCE = new JnotepadConfig();

    public static JnotepadConfig getInstance() {
        return INSTANCE;
    }

    public List<ShortcutKey> getShortcutKeyList() {
        return shortcutKeyList;
    }

    public void setShortcutKeyList(List<ShortcutKey> shortcutKeyList) {
        this.shortcutKeyList = shortcutKeyList;
    }

    public List<Style> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<Style> styleList) {
        this.styleList = styleList;
    }
}
