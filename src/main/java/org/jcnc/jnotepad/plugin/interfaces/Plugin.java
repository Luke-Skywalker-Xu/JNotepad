package org.jcnc.jnotepad.plugin.interfaces;


import java.util.Map;

/**
 * 插件接口
 * <p>
 * 描述插件的基本功能。
 *
 * @author luke gewuyou
 */
public interface Plugin {
    /**
     * 初始化插件
     */
    void initialize();

    /**
     * 执行插件的逻辑
     */
    void execute();

    /**
     * 获取插件的配置参数
     *
     * @return 插件的配置参数
     */
    Map<String, Object> getConfig();

    /**
     * 设置插件的配置参数
     *
     * @param config 插件的配置参数
     */
    void setConfig(Map<String, Object> config);

}
