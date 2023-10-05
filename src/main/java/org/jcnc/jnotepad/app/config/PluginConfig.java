package org.jcnc.jnotepad.app.config;

import org.jcnc.jnotepad.model.entity.PluginDescriptor;

import java.util.List;

/**
 * 插件配置文件
 *
 * @author gewuyou
 */
public class PluginConfig {
    private List<PluginDescriptor> plugins;

    public List<PluginDescriptor> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<PluginDescriptor> plugins) {
        this.plugins = plugins;
    }
}
