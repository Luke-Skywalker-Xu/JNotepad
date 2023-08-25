package org.jcnc.jnotepad.app.config;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import static org.jcnc.jnotepad.constants.TextConstants.CONFIG;
import static org.jcnc.jnotepad.constants.TextConstants.LANGUAGE_MAP;

/**
 * 加载语言配置文件
 *
 * @author gewuyou
 * @see [相关类/方法]
 */
public class LoadLanguageConfig extends LoadJnotepadConfig {
    @Override
    protected void loadConfig(InputStream inputStream) {
        List<LinkedHashMap<String, String>> configData = parseConfig(inputStream);
        String language = "";
        for (LinkedHashMap<String, String> config : configData) {
            language = config.get("language");
            if (language != null) {
                break;
            }
        }
        if (!"".equals(language) && language != null) {
            CONFIG.setLanguagePackName(LANGUAGE_MAP.get(language));
        }
    }
}
