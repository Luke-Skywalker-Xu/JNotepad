package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.plugin.interfaces.Plugin;
import org.jcnc.jnotepad.util.LogUtil;

import java.util.Map;

/**
 * 新按钮插件
 *
 * @author luke
 */
public class ButtonPlugin implements Plugin {

    @Override
    public String getCategoryName() {
        return "新按钮插件";
    }

    @Override
    public String getDisplayName() {
        return "新按钮";
    }

    /**
     * 初始化插件
     */
    @Override
    public void initialize() {
        LogUtil.getLogger(this.getClass()).info("新按钮插件初始化了!");
    }

    @Override
    public void execute() {
        // 在这里实现新按钮插件的逻辑
        LogUtil.getLogger(this.getClass()).info("新按钮插件执行了!");
    }

    /**
     * 获取插件的配置参数
     *
     * @return 插件的配置参数
     */
    @Override
    public Map<String, Object> getConfig() {
        return null;
    }

    /**
     * 设置插件的配置参数
     *
     * @param config 插件的配置参数
     */
    @Override
    public void setConfig(Map<String, Object> config) {

    }
}