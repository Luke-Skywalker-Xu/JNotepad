package org.jcnc.jnotepad.app.config;

import static org.jcnc.jnotepad.constants.TextConstants.PROPERTIES;
import static org.jcnc.jnotepad.constants.TextConstants.TEXT_WRAP;

/**
 * 内存中，运行过程中的全局配置项
 *
 * @author zhaoteng.song
 */
public class GlobalConfig {

    private static final GlobalConfig APP_GLOBAL_CONFIG = new GlobalConfig();

    private GlobalConfig() {
    }

    /**
     * 获取自动换行设置，默认自动换行
     *
     * @return true, 自动换行；false，不自动换行
     */
    public boolean getAutoLineConfig() {
        return Boolean.parseBoolean(PROPERTIES.getProperty(TEXT_WRAP, "true"));
    }

    public void setAutoLineConfig(boolean isAutoLine) {
        String autoLineConfig = String.valueOf(isAutoLine);
        PROPERTIES.setProperty(TEXT_WRAP, autoLineConfig);
    }

    public static GlobalConfig getConfig() {
        return APP_GLOBAL_CONFIG;
    }
}
