package org.jcnc.jnotepad.view;

import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.controller.Controller;

import static org.jcnc.jnotepad.ViewManager.newItem;
import static org.jcnc.jnotepad.ViewManager.openItem;
import static org.jcnc.jnotepad.ViewManager.saveItem;
import static org.jcnc.jnotepad.ViewManager.saveAsItem;
import static org.jcnc.jnotepad.ViewManager.tabPane;
import static org.jcnc.jnotepad.controller.Controller.updateEncodingLabel;
import static org.jcnc.jnotepad.controller.Controller.updateStatusLabel;

public class View {

    public static void initItem() {
        // 初始化菜单项的事件处理器
        newItem.setOnAction(new Controller.NewFileEventHandler());
        openItem.setOnAction(new Controller.OpenFileEventHandler());
        saveItem.setOnAction(new Controller.SaveFileEventHandler());
        saveAsItem.setOnAction(new Controller.SaveAsFileEventHandler());
    }

    public static void initTabPane() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                // 获取新选定的标签页并关联的文本区域
                TextArea textArea = (TextArea) newTab.getContent();

                // 更新状态标签
                updateStatusLabel(textArea);

                // 监听文本光标位置的变化，更新状态标签
                textArea.caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> updateStatusLabel(textArea));

                // 更新编码标签
                updateEncodingLabel(textArea.getText());
            }
        });
    }
}
