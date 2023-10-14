package org.jcnc.jnotepad.app.config;

import org.jcnc.jnotepad.model.entity.PluginDescriptor;

import java.util.List;

/**
 * 插件配置文件
 *
 * <p>
 * 此类用于存储插件的配置信息，包括插件描述符的列表。
 * </p>
 *
 * @author gewuyou
 */
public class PluginConfig {
    private List<PluginDescriptor> plugins;

    /**
     * 获取插件描述符列表
     *
     * @return 插件描述符列表
     */
    public List<PluginDescriptor> getPlugins() {
        return plugins;
    }

    /**
     * 设置插件描述符列表
     *
     * @param plugins 插件描述符列表
     */
    public void setPlugins(List<PluginDescriptor> plugins) {
        this.plugins = plugins;
    }
}
