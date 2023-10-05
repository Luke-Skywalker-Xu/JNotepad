package org.jcnc.jnotepad.controller;

import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.plugin.PluginLoader;

/**
 * 资源控制器
 *
 * @author gewuyou
 */
public class ResourceController {
    private static final ResourceController INSTANCE = new ResourceController();

    private ResourceController() {
    }


    public static ResourceController getInstance() {
        return INSTANCE;
    }

    public void loadResources() {
        // 1. 加载语言
        LocalizationController.initLocal();
        // 2. 加载插件
        PluginLoader.getInstance().loadPlugins();
    }
}
