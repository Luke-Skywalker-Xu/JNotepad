package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.common.manager.ThreadPoolManager;
import org.jcnc.jnotepad.controller.config.PluginConfigController;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.model.entity.PluginDescriptor;
import org.jcnc.jnotepad.plugin.interfaces.Plugin;
import org.jcnc.jnotepad.util.JsonUtil;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 插件加载类
 *
 * @author gewuyou
 */
public class PluginLoader {
    private static final PluginLoader INSTANCE = new PluginLoader();

    private static final ExecutorService THREAD_POOL = ThreadPoolManager.getThreadPool();
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 从插件jar包中读取 json 文件到 PluginDescriptor 类
     *
     * @param pluginJar jar 包
     */
    public static PluginDescriptor readPlugin(File pluginJar) throws IOException {
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
        return JsonUtil.OBJECT_MAPPER.readValue(sb.toString(), PluginDescriptor.class);
    }

    public static PluginLoader getInstance() {
        return INSTANCE;
    }

    /**
     * 检查插件
     *
     * @param configPluginDescriptors 配置文件插件信息
     * @param pluginDescriptor        插件信息类
     * @param pluginDescriptors       插件信息集合
     * @return boolean 是否检查通过
     * @apiNote
     * @since 2023/9/16 14:04
     */
    private static boolean checkPlugin(List<PluginDescriptor> configPluginDescriptors, PluginDescriptor pluginDescriptor, List<PluginDescriptor> pluginDescriptors) {
        // 如果应用程序配置文件中没有该插件则默认禁用
        if (pluginDoesNotExist(pluginDescriptor, configPluginDescriptors)) {
            disabledByDefault(configPluginDescriptors, pluginDescriptor, pluginDescriptors);
            THREAD_POOL.submit(() -> {
                PluginConfigController.getInstance().writeConfig();
                ThreadPoolManager.threadContSelfSubtracting();
            });
            return true;
        }
        // 如果应用程序配置文件中该插件禁用则不加载
        for (PluginDescriptor configPluginDescriptor : configPluginDescriptors) {
            if (disableDoNotLoad(pluginDescriptor, pluginDescriptors, configPluginDescriptor)) {
                return true;
            }
        }
        // 判断该插件是否已经加载
        return loaded(pluginDescriptor, pluginDescriptors);
    }

    /**
     * 插件不存在
     *
     * @param pluginDescriptor        插件描述类
     * @param configPluginDescriptors 配置文件插件信息集合
     * @return boolean 插件不存在
     * @apiNote
     * @since 2023/9/19 19:16
     */
    private static boolean pluginDoesNotExist(PluginDescriptor pluginDescriptor, List<PluginDescriptor> configPluginDescriptors) {
        for (PluginDescriptor configPluginDescriptor : configPluginDescriptors) {
            if ((configPluginDescriptor.getName() + configPluginDescriptor.getAuthor()).equals(pluginDescriptor.getName() + pluginDescriptor.getAuthor())) {
                return false;
            }
        }
        return true;
    }

    private static boolean loaded(PluginDescriptor pluginDescriptor, List<PluginDescriptor> pluginDescriptors) {
        Iterator<PluginDescriptor> iterator = pluginDescriptors.iterator();
        while (iterator.hasNext()) {
            PluginDescriptor plugin = iterator.next();
            if ((plugin.getName() + plugin.getAuthor()).equals(pluginDescriptor.getName() + pluginDescriptor.getAuthor())) {
                if (plugin.getVersion().equals(pluginDescriptor.getVersion())) {
                    return true;
                }
                // 如果当前插件版本更低则更新
                if (plugin.getVersion().compareTo(pluginDescriptor.getVersion()) < 0) {
                    // 删除当前的插件
                    iterator.remove();
                } else {
                    throw new AppException("当前加载的插件版本低于本地版本!");
                }
            }
        }
        return false;
    }

    /**
     * 如果插件被禁用则不加载
     *
     * @param pluginDescriptor       插件描述类
     * @param pluginDescriptors      插件描述类集合
     * @param configPluginDescriptor 配置文件插件信息
     * @return boolean
     * @apiNote
     * @since 2023/9/19 18:45
     */
    private static boolean disableDoNotLoad(PluginDescriptor pluginDescriptor, List<PluginDescriptor> pluginDescriptors, PluginDescriptor configPluginDescriptor) {
        if ((configPluginDescriptor.getName() + configPluginDescriptor.getAuthor()).equals(pluginDescriptor.getName() + pluginDescriptor.getAuthor()) && !configPluginDescriptor.isEnabled()) {
            pluginDescriptor.setEnabled(false);
            pluginDescriptors.add(pluginDescriptor);
            return true;
        }
        return false;
    }

    /**
     * 默认禁用
     *
     * @param configPluginDescriptors 配置文件插件信息
     * @param pluginDescriptor        插件描述类
     * @param pluginDescriptors       插件描述类集合
     * @apiNote
     * @since 2023/9/19 18:48
     */
    private static void disabledByDefault(List<PluginDescriptor> configPluginDescriptors, PluginDescriptor pluginDescriptor, List<PluginDescriptor> pluginDescriptors) {
        pluginDescriptor.setEnabled(false);
        pluginDescriptors.add(pluginDescriptor);
        configPluginDescriptors.add(pluginDescriptor);
    }

    /**
     * 加载插件
     *
     * @param pluginFilePath 插件文件的路径
     */
    public void loadPluginByPath(String pluginFilePath) {
        File file = new File(pluginFilePath);
        loadPluginByFile(file, PluginConfigController.getInstance().getConfig().getPlugins());
    }

    /**
     * 根据文件加载插件
     *
     * @param pluginJar               插件jar包
     * @param configPluginDescriptors 配置文件插件信息集合
     * @apiNote
     * @since 2023/9/16 14:05
     */
    public void loadPluginByFile(File pluginJar, List<PluginDescriptor> configPluginDescriptors) {
        PluginManager pluginManager = PluginManager.getInstance();
        Map<String, List<String>> categories = pluginManager.getLoadedPluginsByCategory();
        List<PluginDescriptor> pluginDescriptors = pluginManager.getPluginDescriptors();
        if (pluginJar.exists() && pluginJar.isFile()) {
            try {
                PluginDescriptor pluginDescriptor = readPlugin(pluginJar);
                // 检查插件状态
                if (checkPlugin(configPluginDescriptors, pluginDescriptor, pluginDescriptors)) {
                    return;
                }
                pluginDescriptor.setEnabled(true);
                pluginDescriptors.add(pluginDescriptor);
                // 创建URLClassLoader以加载Jar文件中的类
                Class<?> pluginClass = loaderJarFileClass(pluginJar, pluginDescriptor);
                if (pluginClass == null) {
                    return;
                }
                Plugin plugin;
                plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                pluginDescriptor.setPlugin(plugin);
                categories.computeIfAbsent(pluginDescriptor.getCategory(), k -> new ArrayList<>()).add(pluginDescriptor.getName());
            } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new AppException(e);
            } catch (ClassNotFoundException e) {
                logger.error("无法找到对应的插件类!", e);
            } catch (NoSuchMethodException e) {
                logger.error("插件类中没有找到指定方法!", e);
            }

        } else {
            logger.info("PluginDescriptor file not found");
        }
    }

    /**
     * 加载类中的class文件并返回插件主类
     *
     * @param pluginJar        插件jar包
     * @param pluginDescriptor 插件描述
     * @return java.lang.Class<?> 插件主类
     * @apiNote
     * @since 2023/9/19 14:00
     */
    private Class<?> loaderJarFileClass(File pluginJar, PluginDescriptor pluginDescriptor) throws IOException, ClassNotFoundException {
        Class<?> pluginClass;
        try (
                URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginJar.toURI().toURL()});
                JarFile jar = new JarFile(pluginJar)
        ) {
            // 加载插件所需的依赖类
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace("/", ".").replace(".class", "");
                    if (!pluginDescriptor.getMainClass().equals(className) && !"module-info".equals(className)) {
                        classLoader.loadClass(className);
                    }
                }
            }
            pluginClass = classLoader.loadClass(pluginDescriptor.getMainClass());
        }
        logger.info("已加载插件：{}", pluginDescriptor.getName());
        return pluginClass;
    }
}
