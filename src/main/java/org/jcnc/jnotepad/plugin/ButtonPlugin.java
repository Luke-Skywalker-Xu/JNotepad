package org.jcnc.jnotepad.plugin;

import org.jcnc.jnotepad.util.LogUtil;

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

    @Override
    public void execute() {
        // 在这里实现新按钮插件的逻辑
        LogUtil.getLogger(this.getClass()).info("新按钮插件执行了!");
    }
}