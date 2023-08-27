package org.jcnc.jnotepad.controller.config;

import org.jcnc.jnotepad.app.config.AppConfig;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.tool.JsonUtil;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.PopUpUtil;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AppConfigController {

    private static final Logger logger = LogUtil.getLogger(AppConfigController.class);

    private static final AppConfigController INSTANCE = new AppConfigController();

    public static AppConfigController getInstance() {
        return INSTANCE;
    }

    /**
     * 配置文件名
     */
    public static final String CONFIG_NAME = "jnotepadConfig.json";

    private AppConfig appConfig;
    private String dir;

    private AppConfigController() {
        setDir(Paths.get(System.getProperty("user.home"), ".jnotepad").toString());
        loadConfig();
    }

    /**
     * 加载配置文件内容
     */
    public void loadConfig() {
        createConfigIfNotExists();
        Path configPath = getConfigPath();

        try {
            logger.info("正在加载配置文件...");
            // 存在则加载
            String configContent = Files.readString(configPath);
            appConfig = JsonUtil.OBJECT_MAPPER.readValue(configContent, AppConfig.class);
        } catch (Exception e) {
            logger.error("加载配置文件错误", e);
            throw new AppException(e);
        }
    }

    /**
     * 配置文件持久化
     */
    public void writeAppConfig() {
        createConfigIfNotExists();
        writeAppConfig(this.appConfig);
    }

    /**
     * 将appConfig对象持久化到配置文件中
     *
     * @param appConfig 应用配置对象
     */
    private void writeAppConfig(AppConfig appConfig) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigPath().toString()))) {
            if (appConfig == null) {
                appConfig = createShortcutKeyJson();
            }
            writer.write(JsonUtil.toJsonString(appConfig));
        } catch (Exception e) {
            logger.error("", e);
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!");
        }
    }

    public void createConfigIfNotExists() {
        Path configPath = getConfigPath();
        if (configPath.toFile().exists()) {
            return;
        }
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        writeAppConfig(null);
    }

    public Path getConfigPath() {
        return Paths.get(getDir(), CONFIG_NAME);
    }

    private AppConfig createShortcutKeyJson() {
        return AppConfig.generateDefaultAppConfig();
    }

    /**
     * 获取当前配置文件所在目录
     *
     * @return
     */
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    private AppConfig getAppConfig() {
        return appConfig;
    }

    /**
     * 获取自动换行设置，默认自动换行
     *
     * @return true, 自动换行；false，不自动换行
     */
    public boolean getAutoLineConfig() {
        return getAppConfig().isTextWrap();
    }

    public void setAutoLineConfig(boolean isAutoLine) {
        getAppConfig().setTextWrap(isAutoLine);
    }

    /**
     * 更新配置文件中的语言设置
     *
     * @param language 更新后的语言设置
     */
    public void updateLanguage(String language) {
        if (getLanguage().equals(language)) {
            return;
        }
        this.appConfig.setLanguage(language);
        writeAppConfig();
    }

    public String getLanguage() {
        return this.appConfig.getLanguage();
    }

    public List<AppConfig.ShortcutKey> getShortcutKey() {
        return this.appConfig.getShortcutKey();
    }
}