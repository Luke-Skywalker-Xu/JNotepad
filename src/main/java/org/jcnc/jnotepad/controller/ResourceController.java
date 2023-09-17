package org.jcnc.jnotepad.controller;

import org.jcnc.jnotepad.controller.config.PluginConfigController;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.plugin.PluginLoader;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 资源控制器：用于加载程序所需的资源
 *
 * @author gewuyou
 */
public class ResourceController {
    private static final ResourceController INSTANCE = new ResourceController();
    Logger logger = LogUtil.getLogger(this.getClass());

    private ResourceController() {
    }


    public static ResourceController getInstance() {
        return INSTANCE;
    }

    public void loadResources() {
        loadPlugins();
    }

    /**
     * 装载插件
     *
     * @since 2023/9/15 21:39
     */
    public void loadPlugins() {
        // 扫描并装载插件
        scanLoadPlugins(PluginConfigController.getInstance().getPlungsPath());
    }


    /**
     * 扫描插件
     *
     * @param pluginsPath 插件路径
     * @apiNote 扫描所有插件，更新配置文件中的插件信息
     * @since 2023/9/16 0:21
     */

    private void scanLoadPlugins(Path pluginsPath) {
        if (!Files.isDirectory(pluginsPath)) {
            try {
                Files.createDirectory(pluginsPath);
            } catch (IOException e) {
                throw new AppException("这不是一个有效的路径!");
            }
        }
        // 获取插件加载器
        PluginLoader pluginLoader = PluginLoader.getInstance();
        try (Stream<Path> pathStream = Files.walk(pluginsPath)) {
            pathStream.filter(path -> path.toString().endsWith(".jar")).forEach(path -> pluginLoader.loadPluginByPath(path.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
