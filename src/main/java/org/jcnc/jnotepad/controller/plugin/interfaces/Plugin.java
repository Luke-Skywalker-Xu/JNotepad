package org.jcnc.jnotepad.controller.plugin.interfaces;


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
     * 销毁资源
     */
    void destroyed();
}
