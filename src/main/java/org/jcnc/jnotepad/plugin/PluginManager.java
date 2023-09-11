package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.plugin.interfaces.Plugin;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
    private final List<Plugin> plugins = new ArrayList<>();
    private final Map<String, List<String>> categories = new HashMap<>();

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return INSTANCE;
    }

    /**
     * 加载插件
     *
     * @param pluginFilePath 插件文件的路径
     */
    public void loadPlugins(String pluginFilePath) {
        File file = new File(pluginFilePath);
        if (file.exists() && file.isFile()) {
            // 创建URLClassLoader以加载Jar文件中的类
            Class<?> pluginClass = null;
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()})) {
                pluginClass = classLoader.loadClass("org.jcnc.jnotepad.plugin.ButtonPlugin");
            } catch (ClassNotFoundException e) {
                logger.error("无法找到对应的插件类!", e);
            } catch (MalformedURLException e) {
                logger.error("无法创建URL格式错误!", e);
            } catch (IOException e) {
                logger.error("IO异常!", e);
            }
            if (pluginClass == null) {
                return;
            }
            Plugin plugin = null;
            try {
                plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                logger.error("发生异常!", e);
            }
            if (plugin == null) {
                return;
            }
            plugins.add(plugin);

            // 添加插件到类别中
            String categoryName = plugin.getCategoryName();
            String displayName = plugin.getDisplayName();
            categories.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(displayName);
        } else {
            LogUtil.getLogger(this.getClass()).info("Plugins file not found: {}", pluginFilePath);
        }
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
}
