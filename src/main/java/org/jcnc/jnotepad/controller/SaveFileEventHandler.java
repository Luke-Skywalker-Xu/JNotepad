package org.jcnc.jnotepad.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.jcnc.jnotepad.ViewManager.tabPane;

// 保存文件事件处理器
public class SaveFileEventHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        Controller controller = new Controller();
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            File file = (File) selectedTab.getUserData();
            if (file == null) {
                controller.saveAsFile();
            } else {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                    TextArea textArea = (TextArea) selectedTab.getContent();
                    String text = textArea.getText();
                    writer.write(text);
                    writer.flush();
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
