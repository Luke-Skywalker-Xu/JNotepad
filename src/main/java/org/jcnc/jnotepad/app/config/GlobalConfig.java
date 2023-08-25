package org.jcnc.jnotepad.app.config;


/**
 * 内存中，运行过程中的全局配置项
 *
 * @author zhaoteng.song
 */
public class GlobalConfig {

    private static final GlobalConfig APP_GLOBAL_CONFIG = new GlobalConfig();
    LocalizationConfig localizationConfig = LocalizationConfig.getLocalizationConfig();

    private GlobalConfig() {
    }

    /**
     * 获取自动换行设置，默认自动换行
     *
     * @return true, 自动换行；false，不自动换行
     */
    public boolean getAutoLineConfig() {
        return Boolean.parseBoolean(localizationConfig.getTextWrap());
    }

    public void setAutoLineConfig(boolean isAutoLine) {
        String autoLineConfig = String.valueOf(isAutoLine);
        localizationConfig.setTextWrap(autoLineConfig);
    }

    public static GlobalConfig getConfig() {
        return APP_GLOBAL_CONFIG;
    }
}
