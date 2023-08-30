package org.jcnc.jnotepad.controller.event.handler.tool;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.ui.setStage.SetStage;


/**
 * 设置按钮事件的事件处理程序。
 * <p>
 * 当用户点击设置的时候,将打开设置窗口。
 *
 * @author 许轲
 */
public class SetBtn implements EventHandler<ActionEvent> {

    /**
     * 打开设置窗口处理事件
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        SetStage.getInstance();
    }


}