package org.jcnc.jnotepad.plugin.interfaces;

/**
 * 插件接口
 * <p>
 * 描述插件的基本功能。
 *
 * @author luke
 */
public interface Plugin extends PluginCategory {

    /**
     * 获取插件的显示名称
     *
     * @return 插件的显示名称
     */
    String getDisplayName();

    /**
     * 初始化插件
     */
    void initialize();

    /**
     * 执行插件的逻辑
     */
    void execute();
}
