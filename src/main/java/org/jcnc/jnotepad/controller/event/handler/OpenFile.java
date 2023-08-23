package org.jcnc.jnotepad.controller.event.handler;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;

import java.io.File;


/**
 * 打开文件的事件处理程序。
 * <p>
 * 当用户选择打开文件时，将创建一个新的文本编辑区，并在Tab页中显示。
 * @author 许轲
 */
public class OpenFile implements EventHandler<ActionEvent> {
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
            Task<Void> openFileTask = new Task<>() {
                @Override
                protected Void call() {
                    // 调用控制器的getText方法，读取文件内容
                    controller.getText(file);
                    // 更新编码标签
                    controller.upDateEncodingLabel(((LineNumberTextArea) JNotepadTabPane.getInstance().getSelected().getContent()).getMainTextArea().getText());
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

            // 创建并启动线程执行任务
            Thread thread = new Thread(openFileTask);
            thread.start();
        }
    }
}
