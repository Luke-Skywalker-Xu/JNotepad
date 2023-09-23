package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.common.manager.ThreadPoolManager;
import org.jcnc.jnotepad.controller.config.PluginConfigController;
import org.jcnc.jnotepad.model.entity.PluginDescriptor;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.jcnc.jnotepad.plugin.PluginLoader.readPlugin;

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
    /**
     * 插件类别
     */
    private final Map<String, List<String>> categories = new HashMap<>();
    /**
     * 插件信息
     */
    private final List<PluginDescriptor> pluginDescriptors = new ArrayList<>();
    Logger logger = LogUtil.getLogger(this.getClass());

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return INSTANCE;
    }


    /**
     * 卸载插件
     *
     * @param pluginDescriptor 插件信息类
     * @since 2023/9/11 12:28
     */
    public void unloadPlugin(PluginDescriptor pluginDescriptor) {
        // 删除集合中的插件信息
        pluginDescriptors.remove(pluginDescriptor);
        PluginConfigController instance = PluginConfigController.getInstance();
        instance.getConfig().getPlugins().remove(pluginDescriptor);
        // 刷新配置
        instance.writeConfig();
        // 删除本地插件jar包
        Path plungsPath = instance.getPlungsPath();
        try (Stream<Path> pathStream = Files.walk(plungsPath)) {
            pathStream.filter(path -> path.toString().endsWith(".jar")).forEach(path -> {
                try {
                    File pluginJar = new File(path.toString());
                    PluginDescriptor temp = readPlugin(pluginJar);
                    if ((temp.getName() + temp.getAuthor()).equals(pluginDescriptor.getName() + pluginDescriptor.getAuthor())) {
                        Files.delete(pluginJar.toPath());
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ThreadPoolManager.threadContSelfSubtracting();
    }

    /**
     * 禁用插件
     *
     * @param pluginDescriptor 需要禁用的某个插件的插件类
     * @apiNote
     * @since 2023/9/11 12:34
     */
    public void disablePlugIn(PluginDescriptor pluginDescriptor) {
        pluginDescriptor.setEnabled(false);
    }

    /**
     * 初始化所有启用的插件
     */
    public void initializePlugins() {
        for (PluginDescriptor pluginDescriptor : pluginDescriptors) {
            if (pluginDescriptor.isEnabled()) {
                pluginDescriptor.getPlugin().initialize();
            }
        }
    }

    /**
     * 执行插件
     *
     * @param pluginDescriptor 需要执行的插件的信息类
     * @apiNote
     * @since 2023/9/16 14:58
     */
    public void executePlugin(PluginDescriptor pluginDescriptor) {
        pluginDescriptor.getPlugin().execute();
    }

    /**
     * 执行加载的插件
     * todo 待移除
     */
    public void executePlugins() {
        for (PluginDescriptor pluginDescriptor : pluginDescriptors) {
            if (pluginDescriptor.isEnabled()) {
                pluginDescriptor.getPlugin().execute();
            }
        }
    }

    /**
     * 销毁插件可能申请的资源
     */
    public void destroyPlugins() {
        for (PluginDescriptor pluginDescriptor : pluginDescriptors) {
            if (pluginDescriptor.isEnabled() && pluginDescriptor.getPlugin() != null) {
                pluginDescriptor.getPlugin().destroyed();
            }
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

    public List<PluginDescriptor> getPluginDescriptors() {
        return pluginDescriptors;
    }

    /**
     * 启用插件
     *
     * @param pluginDescriptor 插件信息类
     */
    public void enablePlugIn(PluginDescriptor pluginDescriptor) {
        pluginDescriptor.setEnabled(true);
    }
}
