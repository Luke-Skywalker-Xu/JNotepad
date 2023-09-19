package org.jcnc.jnotepad.app.config;

import org.jcnc.jnotepad.model.entity.PluginDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 插件配置文件
 *
 * @author gewuyou
 */
public class PluginConfig {
    private List<PluginDescriptor> plugins;

    /**
     * 生成默认的插件配置文件
     *
     * @return org.jcnc.jnotepad.app.config.PluginConfig 插件配置文件
     * @apiNote
     * @since 2023/9/17 0:57
     */
    public static PluginConfig generateDefaultPluginConfig() {
        PluginConfig pluginConfig = new PluginConfig();
        pluginConfig.setPlugins(new ArrayList<>());
        return pluginConfig;
    }

    public List<PluginDescriptor> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<PluginDescriptor> plugins) {
        this.plugins = plugins;
    }
}
