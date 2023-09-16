package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.controller.config.AppConfigController;
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
import java.util.Iterator;
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
    public static PluginInfo readPlugin(File pluginJar) throws IOException {
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
     * 检查插件
     *
     * @param configPluginInfos 配置文件插件信息
     * @param pluginInfo        插件信息类
     * @param pluginInfos       插件信息集合
     * @return boolean 是否检查通过
     * @apiNote
     * @since 2023/9/16 14:04
     */
    private static boolean checkPlugin(List<PluginInfo> configPluginInfos, PluginInfo pluginInfo, List<PluginInfo> pluginInfos) {
        // 如果应用程序配置文件为空则默认插件被禁用
        if (configPluginInfos.isEmpty()) {
            return disabledByDefault(configPluginInfos, pluginInfo, pluginInfos);
        }
        // 如果应用程序配置文件中该插件禁用则不加载
        for (PluginInfo configPluginInfo : configPluginInfos) {
            if (disableDoNotLoad(pluginInfo, pluginInfos, configPluginInfo)) {
                return true;
            }
        }
        // 判断该插件是否已经加载
        return loaded(pluginInfo, pluginInfos);
    }

    private static boolean loaded(PluginInfo pluginInfo, List<PluginInfo> pluginInfos) {
        Iterator<PluginInfo> iterator = pluginInfos.iterator();
        while (iterator.hasNext()) {
            PluginInfo plugin = iterator.next();
            if ((plugin.getName() + plugin.getAuthor()).equals(pluginInfo.getName() + pluginInfo.getAuthor())) {
                if (plugin.getVersion().equals(pluginInfo.getVersion())) {
                    return true;
                }
                // 如果当前插件版本更低则更新
                if (plugin.getVersion().compareTo(pluginInfo.getVersion()) < 0) {
                    // 删除当前的插件
                    iterator.remove();
                } else {
                    throw new AppException("当前加载的插件版本低于本地版本!");
                }
            }
        }
        return false;
    }

    private static boolean disableDoNotLoad(PluginInfo pluginInfo, List<PluginInfo> pluginInfos, PluginInfo configPluginInfo) {
        if ((configPluginInfo.getName() + configPluginInfo.getAuthor()).equals(pluginInfo.getName() + pluginInfo.getAuthor()) && !configPluginInfo.isEnabled()) {
            pluginInfo.setEnabled(false);
            pluginInfos.add(pluginInfo);
            return true;
        }
        return false;
    }

    private static boolean disabledByDefault(List<PluginInfo> configPluginInfos, PluginInfo pluginInfo, List<PluginInfo> pluginInfos) {
        pluginInfo.setEnabled(false);
        pluginInfos.add(pluginInfo);
        configPluginInfos.add(pluginInfo);
        AppConfigController.getInstance().writeAppConfig();
        return true;
    }

    /**
     * 加载插件
     *
     * @param pluginFilePath 插件文件的路径
     */
    public void loadPluginByPath(String pluginFilePath) {
        File file = new File(pluginFilePath);
        loadPluginByFile(file, AppConfigController.getInstance().getAppConfig().getPlugins());
    }

    /**
     * 根据文件加载插件
     *
     * @param pluginJar         插件jar包
     * @param configPluginInfos 配置文件插件信息集合
     * @apiNote
     * @since 2023/9/16 14:05
     */
    public void loadPluginByFile(File pluginJar, List<PluginInfo> configPluginInfos) {
        PluginManager pluginManager = PluginManager.getInstance();
        Map<String, List<String>> categories = pluginManager.getLoadedPluginsByCategory();
        List<PluginInfo> pluginInfos = pluginManager.getPluginInfos();
        if (pluginJar.exists() && pluginJar.isFile()) {
            try {
                PluginInfo pluginInfo = readPlugin(pluginJar);
                // 检查插件状态
                if (checkPlugin(configPluginInfos, pluginInfo, pluginInfos)) {
                    return;
                }
                pluginInfo.setEnabled(true);
                pluginInfos.add(pluginInfo);
                // 创建URLClassLoader以加载Jar文件中的类
                Class<?> pluginClass;
                try (URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginJar.toURI().toURL()})) {
                    logger.info("{}", pluginInfo.getMainClass());
                    pluginClass = classLoader.loadClass(pluginInfo.getMainClass());
                }
                if (pluginClass == null) {
                    return;
                }
                Plugin plugin;
                plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                pluginInfo.setPlugin(plugin);
                categories.computeIfAbsent(pluginInfo.getCategory(), k -> new ArrayList<>()).add(pluginInfo.getName());
            } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new AppException(e);
            } catch (ClassNotFoundException e) {
                logger.error("无法找到对应的插件类!", e);
            } catch (NoSuchMethodException e) {
                logger.error("插件类中没有找到指定方法!", e);
            }

        } else {
            LogUtil.getLogger(this.getClass()).info("PluginInfo file not found");
        }
    }
}
