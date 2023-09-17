package org.jcnc.jnotepad.controller.config;

import org.jcnc.jnotepad.app.config.AppConfig;
import org.jcnc.jnotepad.model.entity.ShortcutKey;

import java.nio.file.Paths;
import java.util.List;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;

/**
 * 应用程序配置控制器
 *
 * <p>该类负责管理应用程序的配置文件，包括加载、持久化和更新配置信息等操作。</p>
 *
 * @author songdragon
 */
public class AppConfigController extends BaseConfigController<AppConfig> {

    /**
     * 配置文件名
     */
    public static final String CONFIG_NAME = "jnotepadConfig.json";
    private static final AppConfigController INSTANCE = new AppConfigController();
    private String configDir;

    private AppConfigController() {
        configDir = Paths.get(System.getProperty(DEFAULT_PROPERTY), ".jnotepad").toString();
        loadConfig();
    }

    /**
     * 获取 AppConfigController 的实例。
     *
     * @return AppConfigController 的实例
     */
    public static AppConfigController getInstance() {
        return INSTANCE;
    }

    /**
     * 获取配置文件Class类
     *
     * @return 配置文件Class类
     */
    @Override
    protected Class<AppConfig> getConfigClass() {
        return AppConfig.class;
    }

    /**
     * 生成默认的配置文件
     *
     * @return 默认的配置文件
     */
    @Override
    protected AppConfig generateDefaultConfig() {
        return AppConfig.generateDefaultAppConfig();
    }

    /**
     * 获取配置文件名称
     *
     * @return 配置文件名称
     */
    @Override
    protected String getConfigName() {
        return CONFIG_NAME;
    }

    /**
     * 获取配置文件文件夹路径
     *
     * @return 配置文件夹路径
     */
    @Override
    protected String getConfigDir() {
        return configDir;
    }

    public void setConfigDir(String configDir) {
        this.configDir = configDir;
    }

    /**
     * 获取自动换行设置，默认自动换行。
     *
     * @return true，自动换行；false，不自动换行
     */
    public boolean getAutoLineConfig() {
        return getConfig().isTextWrap();
    }

    public void setAutoLineConfig(boolean isAutoLine) {
        getConfig().setTextWrap(isAutoLine);
    }

    /**
     * 更新配置文件中的语言设置。
     *
     * @param language 更新后的语言设置
     */
    public void updateLanguage(String language) {
        if (getLanguage().equals(language)) {
            return;
        }
        getConfig().setLanguage(language);
        writeConfig();
    }

    /**
     * 获取当前的语言设置。
     *
     * @return 语言设置
     */
    public String getLanguage() {
        return getConfig().getLanguage();
    }

    /**
     * 获取快捷键设置。
     *
     * @return 快捷键设置列表
     */
    public List<ShortcutKey> getShortcutKey() {
        return getConfig().getShortcutKey();
    }
}
