package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.model.entity.PluginInfo;
import org.jcnc.jnotepad.plugin.interfaces.Plugin;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 插件管理器
 * <p>
 * 该类用于管理插件的加载和执行。
 * 插件可以通过加载外部JAR文件中的类来扩展应用程序的功能。
 *
 * @author luke
 */
public class PluginManager {
    private static final PluginManager INSTANCE = new PluginManager();
    Logger logger = LogUtil.getLogger(this.getClass());
    /**
     * 插件集合
     */
    private final List<Plugin> plugins = new ArrayList<>();
    /**
     * 插件类别
     */
    private final Map<String, List<String>> categories = new HashMap<>();
    /**
     * 插件信息
     */

    private final Map<String, PluginInfo> pluginInfos = new HashMap<>();

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return INSTANCE;
    }


    /**
     * 卸载插件
     *
     * @param pluginClassName 插件类名
     * @since 2023/9/11 12:28
     */
    public void unloadPlugin(String pluginClassName) {
        //todo Unload the plugin and remove it from the list
    }

    /**
     * 禁用插件
     *
     * @param pluginClassName 禁用某个插件
     * @apiNote
     * @since 2023/9/11 12:34
     */
    public void disablePlugIn(String pluginClassName) {
        //todo Disable the plugin
    }

    /**
     * 执行加载的插件
     */
    public void executePlugins() {
        for (Plugin plugin : plugins) {
            plugin.execute();
        }
    }

    /**
     * 获取按类别分类的已加载插件
     *
     * @return 插件类别映射
     */
    public Map<String, List<String>> getLoadedPluginsByCategory() {
        return categories;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public Map<String, PluginInfo> getPluginInfos() {
        return pluginInfos;
    }
}
