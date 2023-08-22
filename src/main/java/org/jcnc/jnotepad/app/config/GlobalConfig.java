package org.jcnc.jnotepad.app.config;

import org.jcnc.jnotepad.Interface.ConfigInterface;
import org.jcnc.jnotepad.init.Config;

import java.util.Properties;

public class GlobalConfig {

    public static final String TEXT_WRAP = "text.wrap";
    private static final GlobalConfig APP_GLOBAL_CONFIG = new GlobalConfig();

    private ConfigInterface configLoader;
    private Properties properties;

    private GlobalConfig() {
        this.configLoader = new Config();
        properties = this.configLoader.readPropertiesFromFile();
    }

    /**
     * 获取自动换行设置，默认自动换行
     *
     * @return true, 自动换行；false，不自动换行
     */
    public boolean getAutoLineConfig() {
        return Boolean.parseBoolean(this.properties.getProperty(TEXT_WRAP, "true"));
    }

    public void setAutoLineConfig(boolean isAutoLine) {
        String autoLineConfig = String.valueOf(isAutoLine);
        this.properties.setProperty(TEXT_WRAP, autoLineConfig);
    }

    public static GlobalConfig getConfig() {
        return APP_GLOBAL_CONFIG;
    }
}
