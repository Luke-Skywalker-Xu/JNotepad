package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jcnc.jnotepad.model.entity.ShortcutKey;

import java.util.List;

/**
 * 用户配置文件类
 *
 * <p>
 * 此类用于存储用户的配置信息，包括语言设置、文本自动换行设置和快捷键配置。
 * </p>
 *
 * @author luke
 *
 */
public class UserConfig {

    private String language;
    @JsonIgnore
    private boolean textWrap;
    private List<ShortcutKey> shortcutKey;

    /**
     * 获取语言设置
     *
     * @return 语言设置
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置语言设置
     *
     * @param language 语言设置
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 获取快捷键配置列表
     *
     * @return 快捷键配置列表
     */
    public List<ShortcutKey> getShortcutKey() {
        return shortcutKey;
    }

    /**
     * 设置快捷键配置列表
     *
     * @param shortcutKey 快捷键配置列表
     */
    public void setShortcutKey(List<ShortcutKey> shortcutKey) {
        this.shortcutKey = shortcutKey;
    }

    /**
     * 获取文本自动换行设置
     *
     * @return 是否启用文本自动换行
     */
    public boolean isTextWrap() {
        return textWrap;
    }

    /**
     * 设置文本自动换行设置
     *
     * @param textWrap 是否启用文本自动换行
     */
    public void setTextWrap(boolean textWrap) {
        this.textWrap = textWrap;
    }
}
