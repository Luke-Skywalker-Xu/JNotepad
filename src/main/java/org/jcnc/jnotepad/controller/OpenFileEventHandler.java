package org.jcnc.jnotepad.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;

import static org.jcnc.jnotepad.ViewManager.tabPane;

// 打开文件事件处理器
public  class OpenFileEventHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        Controller controller=new Controller();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Task<Void> openFileTask = new Task<>() {
                @Override
                protected Void call() {
                    controller.getText(file);
                    controller.upDateEncodingLabel(((TextArea) tabPane.getSelectionModel().getSelectedItem().getContent()).getText());
                    return null;
                }
            };

            openFileTask.setOnSucceeded(e -> {
                // 在需要时处理成功
            });

            openFileTask.setOnFailed(e -> {
                // 在需要时处理失败
            });

            Thread thread = new Thread(openFileTask);
            thread.start();
        }
    }
}
