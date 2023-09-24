package org.jcnc.jnotepad.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jcnc.jnotepad.plugin.interfaces.Plugin;

import java.util.Objects;

/**
 * 插件信息
 *
 * @author gewuyou
 */
public class PluginDescriptor {
    /**
     * 插件id
     */
    private String id;
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

    public PluginDescriptor() {
    }

    public PluginDescriptor(PluginDescriptor pluginDescriptor) {
        this.id = pluginDescriptor.getId();
        this.name = pluginDescriptor.getName();
        this.version = pluginDescriptor.getVersion();
        this.enabled = pluginDescriptor.isEnabled();
        this.author = pluginDescriptor.getAuthor();
        this.category = pluginDescriptor.getCategory();
        this.icon = pluginDescriptor.getIcon();
        this.size = pluginDescriptor.getSize();
        this.description = pluginDescriptor.getDescription();
        this.detailedIntroduction = pluginDescriptor.getDetailedIntroduction();
        this.log = pluginDescriptor.getLog();
        this.pluginUrl = pluginDescriptor.getPluginUrl();
        this.mainClass = pluginDescriptor.getMainClass();
        this.assetFolder = pluginDescriptor.getAssetFolder();
        this.readMe = pluginDescriptor.getReadMe();
        this.score = pluginDescriptor.getScore();
        this.plugin = pluginDescriptor.getPlugin();
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, version, enabled, author, category, icon, size, description, detailedIntroduction, log, pluginUrl, mainClass, assetFolder, readMe, score, plugin);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PluginDescriptor other = (PluginDescriptor) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(name, other.name) &&
                Objects.equals(version, other.version) &&
                enabled == other.enabled &&
                Objects.equals(author, other.author) &&
                Objects.equals(category, other.category) &&
                Objects.equals(icon, other.icon) &&
                Objects.equals(size, other.size) &&
                Objects.equals(description, other.description) &&
                Objects.equals(detailedIntroduction, other.detailedIntroduction) &&
                Objects.equals(log, other.log) &&
                Objects.equals(pluginUrl, other.pluginUrl) &&
                Objects.equals(mainClass, other.mainClass) &&
                Objects.equals(assetFolder, other.assetFolder) &&
                Objects.equals(readMe, other.readMe) &&
                Objects.equals(score, other.score) &&
                Objects.equals(plugin, other.plugin);
    }
}
