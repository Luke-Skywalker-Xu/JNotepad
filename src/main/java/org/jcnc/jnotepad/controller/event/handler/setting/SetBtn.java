package org.jcnc.jnotepad.controller.event.handler.setting;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.component.stage.setting.SetStage;

/**
 * 设置按钮事件的事件处理程序。
 *
 * <p>当用户点击设置按钮时，将打开设置窗口。</p>
 *
 * @author 许轲
 */
public class SetBtn implements EventHandler<ActionEvent> {
    /**
     * 标志变量，跟踪Stage是否已创建
     */
    private boolean isStageCreated = false;

    /**
     * 打开设置窗口处理事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        if (!isStageCreated) {
            SetStage.getInstance().openSetStage();
            // 设置标志变量为true，表示Stage已创建
            isStageCreated = true;
        }
    }
}

