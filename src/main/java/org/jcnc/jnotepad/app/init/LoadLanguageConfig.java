package org.jcnc.jnotepad.app.init;

import org.jcnc.jnotepad.app.config.LocalizationConfig;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.slf4j.Logger;

import java.io.InputStream;

import static org.jcnc.jnotepad.constants.TextConstants.LANGUAGE_FILE_MAP;

/**
 * 加载语言配置文件
 *
 * @author gewuyou
 */
public class LoadLanguageConfig extends LoadJnotepadConfig<String> {
    Logger log = LogUtil.getLogger(this.getClass());

    LocalizationConfig localizationConfig = LocalizationConfig.getLocalizationConfig();

    @Override
    protected String parseConfig(InputStream inputStream) {
        return getConfigJson(inputStream).get("language").asText();
    }

    @Override
    protected void loadConfig(InputStream inputStream) {
        log.info("正在加载语言配置文件...");
        String language = parseConfig(inputStream);
        if (!"".equals(language) && language != null) {
            log.info("正在加载语言包:{}", language);
            localizationConfig.setLanguagePackName(LANGUAGE_FILE_MAP.get(language));
            // 刷新语言包
            localizationConfig.initLocalizationConfig();
            JNotepadMenuBar jNotepadMenuBar = JNotepadMenuBar.getMenuBar();
            // 刷新菜单栏
            jNotepadMenuBar.initMenuBar();
            jNotepadMenuBar.toggleLanguageCheck(language);
        }
    }
}
