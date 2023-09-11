package org.jcnc.jnotepad.model.entity;

import lombok.Data;

/**
 * 插件类
 *
 * @author gewuyou
 */
@Data
public class Plugins {
    /**
     * 插件名称
     */
    private String pluginName;
    /**
     * 插件版本
     */
    private String version;
    /**
     * 作者
     */
    private String author;
    /**
     * 插件描述
     */
    private String description;
}
