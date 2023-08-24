package org.jcnc.jnotepad.controller.event.handler;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.controller.manager.Controller;

import java.io.File;


/**
 * 打开文件的事件处理程序。
 * <p>
 * 当用户选择打开文件时，将创建一个新的文本编辑区，并在Tab页中显示。
 *
 * @author 许轲
 */
public class OpenFile extends OpenHandler {
    /**
     * 处理打开文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        // 获取控制器
        Controller controller = Controller.getInstance();
        // 创建文件选择器
        FileChooser fileChooser = new FileChooser();
        // 显示文件选择对话框，并获取选中的文件
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // 创建打开文件的任务
            Task<Void> openFileTask = getVoidTask(controller, file);
            // 创建并启动线程执行任务
            Thread thread = new Thread(openFileTask);
            thread.start();
        }
    }
}
