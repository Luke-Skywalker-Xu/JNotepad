package org.jcnc.jnotepad.plugin;

import java.io.File;
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
    private final List<Plugin> plugins;
    private final Map<String, List<String>> categories;

    /**
     * 构造方法，初始化插件列表和类别映射
     */
    public PluginManager() {
        plugins = new ArrayList<>();
        categories = new HashMap<>();
    }

    /**
     * 加载插件
     *
     * @param pluginFilePath 插件文件的路径
     * @throws Exception 如果加载插件时发生异常
     */
    public void loadPlugins(String pluginFilePath) throws Exception {
        File file = new File(pluginFilePath);

        if (file.exists() && file.isFile()) {
            // 创建URLClassLoader以加载Jar文件中的类
            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            Class<?> pluginClass = classLoader.loadClass("org.jcnc.jnotepad.plugin.ButtonPlugin");
            Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
            plugins.add(plugin);

            // 添加插件到类别中
            String categoryName = plugin.getCategoryName();
            String displayName = plugin.getDisplayName();
            categories.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(displayName);
        } else {
            System.err.println("Plugin file not found: " + pluginFilePath);
        }
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
