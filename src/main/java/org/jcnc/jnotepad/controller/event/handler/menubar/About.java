package org.jcnc.jnotepad.controller.event.handler.menubar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 打开关于页面程序。
 *
 * <p>当用户选择关于时，将创建一个新的Stage。</p>
 *
 * @author 许轲
 */
public class About implements EventHandler<ActionEvent> {
    /**
     * 处理关于页面。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        addAboutStage();
    }

    /**
     * 关于页面。
     */
    public void addAboutStage() {

    }
}
