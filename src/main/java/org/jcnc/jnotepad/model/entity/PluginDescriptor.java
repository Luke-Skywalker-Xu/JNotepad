package org.jcnc.jnotepad.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jcnc.jnotepad.plugin.interfaces.Plugin;

/**
 * 插件信息
 *
 * @author gewuyou
 */
public class PluginDescriptor {
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
     * 图标
     */
    private String icon;

    /**
     * 插件大小
     */
    private Integer size;
    /**
     * 描述
     */
    private String description;
    /**
     * 详细介绍
     */
    private String detailedIntroduction;
    /**
     * 插件日志
     */
    private String log;
    /**
     * 插件网址
     */
    private String pluginUrl;
    /**
     * 主类名称
     */
    private String mainClass;
    /**
     * 资源文件夹
     */
    private String assetFolder;
    /**
     * ReadMe
     */
    private String readMe;

    /**
     * 插件评分
     */
    @JsonIgnore
    private Float score;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedIntroduction() {
        return detailedIntroduction;
    }

    public void setDetailedIntroduction(String detailedIntroduction) {
        this.detailedIntroduction = detailedIntroduction;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPluginUrl() {
        return pluginUrl;
    }

    public void setPluginUrl(String pluginUrl) {
        this.pluginUrl = pluginUrl;
    }

    public String getAssetFolder() {
        return assetFolder;
    }

    public void setAssetFolder(String assetFolder) {
        this.assetFolder = assetFolder;
    }

    public String getReadMe() {
        return readMe;
    }

    public void setReadMe(String readMe) {
        this.readMe = readMe;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
