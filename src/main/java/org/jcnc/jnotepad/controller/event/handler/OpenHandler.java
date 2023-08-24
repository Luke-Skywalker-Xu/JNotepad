package org.jcnc.jnotepad.controller.event.handler;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.controller.manager.Controller;

import java.io.File;

/**
 * 打开抽象类
 *
 * @author gewuyou
 */
public abstract class OpenHandler implements EventHandler<ActionEvent> {
    /**
     * 获取空返回值任务
     *
     * @param controller 控制器
     * @param file       文件
     * @return javafx.concurrent.Task<java.lang.Void>
     * @apiNote
     */
    protected Task<Void> getVoidTask(Controller controller, File file) {
        Task<Void> openFileTask = new Task<>() {
            @Override
            protected Void call() {
                // 调用控制器的getText方法，读取文件内容
                controller.getText(file);
                return null;
            }
        };

        // 设置任务成功完成时的处理逻辑
        openFileTask.setOnSucceeded(e -> {
            // 处理成功的逻辑
        });

        // 设置任务失败时的处理逻辑
        openFileTask.setOnFailed(e -> {
            // 处理失败的逻辑
        });
        return openFileTask;
    }
}
