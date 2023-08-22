package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 换行事件处理，针对当前选中tab进行格式化。
 * 配置变更时：
 * 1. 更新内存全局配置, see {@linkplain org.jcnc.jnotepad.app.config.GlobalConfig}
 * 2. 对当前tab生效配置。每次tab切换，根据全局配置设置进行变更
 * <p>
 * 用于在文本区域中插入一个换行符。
 *
 * @Deprecated 事件处理使用item的listener实现
 *
 * @author 许轲
 */
@Deprecated
public class LineFeed implements EventHandler<ActionEvent> {

    /**
     * 构造函数，初始化 LineFeed 对象。
     */
    public LineFeed() {
    }

    /**
     * 处理事件的方法，将一个换行符插入到文本区域的末尾。
     *
     * @param event 触发的事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        //

    }
}
