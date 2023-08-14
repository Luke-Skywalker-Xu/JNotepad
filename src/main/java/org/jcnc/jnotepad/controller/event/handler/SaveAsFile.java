package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.controller.manager.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.jcnc.jnotepad.view.manager.ViewManager.tabPane;

/**
 * 保存文件事件处理器。
 * <p>
 * 当用户选择保存文件时，当用户选择另存为文件菜单或按钮时，
 * 会弹出一个保存文件对话框，用户选择保存位置和文件名后，
 * 将当前文本编辑区的内容保存到指定文件中，
 * 并更新Tab页上的文件名和UserData。
 */
public class SaveAsFile implements EventHandler<ActionEvent> {
    /**
     * 处理保存文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        Controller controller = new Controller();
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("新建文本");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    TextArea textArea = (TextArea) selectedTab.getContent(); // 获取当前Tab页的文本编辑区
                    controller.autoSave(textArea);// 自动保存
                    String text = textArea.getText();
                    writer.write(text); // 写入文件内容
                    writer.flush();
                    writer.close();
                    selectedTab.setText(file.getName()); // 更新Tab页标签上的文件名
                    selectedTab.setUserData(file); // 将文件对象保存到Tab页的UserData中
                } catch (IOException ignored) {
                }
            }
        }
    }
}
