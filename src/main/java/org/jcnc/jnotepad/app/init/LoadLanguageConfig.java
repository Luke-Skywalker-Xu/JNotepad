package org.jcnc.jnotepad.app.init;

import org.jcnc.jnotepad.app.config.LocalizationConfig;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.ui.status.JNotepadStatusBox;
import org.slf4j.Logger;

import java.io.InputStream;

/**
 * 加载语言配置文件
 *
 * @author gewuyou
 */
public class LoadLanguageConfig extends LoadJnotepadConfig<String> {
    Logger log = LogUtil.getLogger(this.getClass());

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
            // 刷新语言包
            LocalizationConfig.setCurrentLocal(language);
            JNotepadMenuBar jNotepadMenuBar = JNotepadMenuBar.getMenuBar();
            // 刷新菜单栏
            jNotepadMenuBar.toggleLanguageCheck(language);
            JNotepadStatusBox.getInstance().initStatusBox();
        }
    }
}
