package org.jcnc.jnotepad.plgin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luke
 */
public class PluginManager {
    private final List<Plugin> plugins;
    private final Map<String, List<String>> categories;

    public PluginManager() {
        plugins = new ArrayList<>();
        categories = new HashMap<>();
    }

    public void loadPlugins(String pluginFilePath) {
        try {
            File file = new File(pluginFilePath);

            if (file.exists() && file.isFile()) {
                // 创建URLClassLoader以加载Jar文件中的类
                URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
                Class<?> pluginClass = classLoader.loadClass("org.jcnc.jnotepad.plgin.ButtonPlugin");
                Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                plugins.add(plugin);

                // 添加插件到类别中
                String categoryName = plugin.getCategoryName();
                String displayName = plugin.getDisplayName();
                categories.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(displayName);
            } else {
                System.err.println("Plugin file not found: " + pluginFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executePlugins() {
        for (Plugin plugin : plugins) {
            plugin.execute();
        }
    }

    public Map<String, List<String>> getLoadedPluginsByCategory() {
        return categories;
    }
}
