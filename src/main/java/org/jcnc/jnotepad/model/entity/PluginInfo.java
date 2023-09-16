package org.jcnc.jnotepad.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jcnc.jnotepad.plugin.interfaces.Plugin;

/**
 * 插件信息
 *
 * @author gewuyou
 */
public class PluginInfo {
    /**
     * 插件名称
     */
    private String name;
    /**
     * 插件版本
     */
    private String version;
    /**
     * 启用状态
     */
    private boolean enabled;
    /**
     * 作者名称
     */
    private String author;
    /**
     * 类别
     */
    private String category;

    /**
     * 主类名称
     */
    private String mainClass;
    /**
     * 插件类
     */
    @JsonIgnore
    private Plugin plugin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
}
