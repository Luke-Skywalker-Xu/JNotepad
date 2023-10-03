package org.jcnc.jnotepad.controller.config;

import org.jcnc.jnotepad.app.config.PluginConfig;
import org.jcnc.jnotepad.plugin.api.core.controller.config.BaseConfigController;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;

/**
 * 插件控制器
 *
 * @author gewuyou
 */
public class PluginConfigController extends BaseConfigController<PluginConfig> {
    /**
     * 插件配置文件
     */
    public static final String CONFIG_NAME = "pluginConfig.json";
    private static final PluginConfigController INSTANCE = new PluginConfigController();

    private String configDir;
    private String pluginsDir;

    private PluginConfigController() {
        configDir = Paths.get(System.getProperty(DEFAULT_PROPERTY), ".jnotepad").toString();
        setPluginsDir(Paths.get(System.getProperty(DEFAULT_PROPERTY), ".jnotepad", "plugins").toString());
        loadConfig();
    }

    public static PluginConfigController getInstance() {
        return INSTANCE;
    }

    /**
     * 获取配置文件Class类
     *
     * @return 配置文件Class类
     */
    @Override
    protected Class<PluginConfig> getConfigClass() {
        return PluginConfig.class;
    }

    /**
     * 生成默认的配置文件
     *
     * @return 默认的配置文件
     */
    @Override
    protected PluginConfig generateDefaultConfig() {
        return PluginConfig.generateDefaultPluginConfig();
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
     * 获取插件路径
     *
     * @return 插件路径
     */
    public Path getPlungsPath() {
        return Paths.get(getPluginsDir());
    }

    public String getPluginsDir() {
        return pluginsDir;
    }

    public void setPluginsDir(String pluginsDir) {
        this.pluginsDir = pluginsDir;
    }
}
