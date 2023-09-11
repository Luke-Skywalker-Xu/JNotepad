package org.jcnc.jnotepad.model.entity;

/**
 * 插件信息
 *
 * @author gewuyou
 */
public class PluginInfo {
    /**
     * 插件名称
     */
    private String pluginName;
    /**
     * 插件版本
     */
    private String version;
    /**
     * 启用状态
     */
    private boolean enabled;

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
