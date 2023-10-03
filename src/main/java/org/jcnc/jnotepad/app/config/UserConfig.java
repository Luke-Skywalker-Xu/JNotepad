package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jcnc.jnotepad.model.entity.ShortcutKey;

import java.util.List;

/**
 * 用户配置文件类
 *
 * @author 许轲
 */
public class UserConfig {

    private String language;
    @JsonIgnore
    private boolean textWrap;
    private List<ShortcutKey> shortcutKey;


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
