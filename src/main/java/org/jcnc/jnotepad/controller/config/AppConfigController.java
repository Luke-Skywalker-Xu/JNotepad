package org.jcnc.jnotepad.controller.config;

import org.jcnc.jnotepad.app.config.AppConfig;
import org.jcnc.jnotepad.plugin.api.core.controller.config.BaseConfigController;

import java.nio.file.Paths;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;

/**
 * 应用程序配置文件控制器
 *
 * @author gewuyou
 */
public class AppConfigController extends BaseConfigController<AppConfig> {

    private static final AppConfigController INSTANCE = new AppConfigController();

    public static AppConfigController getInstance() {
        return INSTANCE;
    }

    /**
     * 配置文件名
     */
    public static final String CONFIG_NAME = "appConfig.json";

    private final String configDir;

    public AppConfigController() {
        configDir = Paths.get(System.getProperty(DEFAULT_PROPERTY), ".jnotepad", ROOT_CONFIG_DIR).toString();
        loadConfig();
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

    /**
     * 创建配置文件实体
     *
     * @return 默认的配置文件实体
     * @apiNote 返回默认的配置文件实体用于序列化json
     */
    @Override
    public AppConfig generateDefaultConfig() {
        AppConfig config = new AppConfig();
        config.setRootPath(System.getProperty(DEFAULT_PROPERTY));
        return config;
    }
}
