package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.model.entity.PluginInfo;
import org.jcnc.jnotepad.plugin.interfaces.Plugin;
import org.jcnc.jnotepad.util.JsonUtil;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 插件加载类
 *
 * @author gewuyou
 */
public class PluginLoader {
    private static final PluginLoader INSTANCE = new PluginLoader();
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 从插件jar包中读取 json 文件到 PluginInfo 类
     *
     * @param pluginJar jar 包
     */
    private static PluginInfo readPlugin(File pluginJar) throws IOException {
        InputStream is;
        StringBuilder sb;
        try (JarFile jarFile = new JarFile(pluginJar)) {
            ZipEntry zipEntry = jarFile.getEntry("META-INF/plugin.json");
            is = jarFile.getInputStream(zipEntry);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String temp;
                sb = new StringBuilder();
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            }
        }
        return JsonUtil.OBJECT_MAPPER.readValue(sb.toString(), PluginInfo.class);
    }

    public static PluginLoader getInstance() {
        return INSTANCE;
    }

    /**
     * 加载插件
     *
     * @param pluginFilePath 插件文件的路径
     */
    public void loadPlugins(String pluginFilePath) {
        PluginManager pluginManager = PluginManager.getInstance();
        List<Plugin> plugins = pluginManager.getPlugins();
        Map<String, List<String>> categories = pluginManager.getLoadedPluginsByCategory();
        Map<String, PluginInfo> pluginInfos = pluginManager.getPluginInfos();
        File file = new File(pluginFilePath);
        if (file.exists() && file.isFile()) {
            try {
                PluginInfo pluginInfo = readPlugin(file);
                pluginInfos.put(pluginInfo.getName(), pluginInfo);
                // 创建URLClassLoader以加载Jar文件中的类
                Class<?> pluginClass;
                try (URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()})) {
                    pluginClass = classLoader.loadClass(pluginInfo.getMainClass());
                }
                if (pluginClass == null) {
                    return;
                }
                Plugin plugin;
                plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                plugins.add(plugin);
                // 添加插件到类别中
                String categoryName = plugin.getCategoryName();
                String displayName = plugin.getDisplayName();
                categories.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(displayName);
            } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new AppException(e);
            } catch (ClassNotFoundException e) {
                logger.error("无法找到对应的插件类!", e);
            } catch (NoSuchMethodException e) {
                logger.error("插件类中没有找到指定方法!", e);
            }

        } else {
            LogUtil.getLogger(this.getClass()).info("PluginInfo file not found: {}", pluginFilePath);
        }
    }
}
