package org.jcnc.jnotepad.controller.config;

import org.jcnc.jnotepad.api.core.controller.config.BaseConfigController;
import org.jcnc.jnotepad.app.config.PluginConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.jcnc.jnotepad.app.common.constants.AppConstants.PROGRAM_FILE_DIRECTORY;

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
        String rootPath = AppConfigController.getInstance().getConfig().getRootPath();
        configDir = Paths.get(rootPath, PROGRAM_FILE_DIRECTORY, ROOT_CONFIG_DIR).toString();
        setPluginsDir(Paths.get(rootPath, PROGRAM_FILE_DIRECTORY, "plugins").toString());
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
     * 创建配置文件实体
     *
     * @return 默认的配置文件实体
     * @apiNote 返回默认的配置文件实体用于序列化json
     */
    @Override
    public PluginConfig generateDefaultConfig() {
        PluginConfig pluginConfig = new PluginConfig();
        pluginConfig.setPlugins(new ArrayList<>());
        return pluginConfig;
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
