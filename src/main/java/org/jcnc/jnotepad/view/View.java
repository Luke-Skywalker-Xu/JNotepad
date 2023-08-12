package org.jcnc.jnotepad.view;

import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.controller.Controller;

import static org.jcnc.jnotepad.ViewManager.*;


public class View {

    public static void initItem() {
        // 初始化菜单项的事件处理器
        newItem.setOnAction(new Controller().getNewFileEventHandler(new TextArea()));
        openItem.setOnAction(new Controller().getOpenFileEventHandler());
        saveItem.setOnAction(new Controller().getSaveFileEventHandler());
        saveAsItem.setOnAction(new Controller().getSaveAsFileEventHandler());
        lineFeedItem.setOnAction(new Controller().getLineFeedEventHandler(new TextArea()));

    }

    public static void initTabPane() {
        Controller controller = new Controller();

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                // 获取新选定的标签页并关联的文本区域
                TextArea textArea = (TextArea) newTab.getContent();

                // 更新状态标签
                controller.updateStatusLabel(textArea);

                // 监听文本光标位置的变化，更新状态标签
                textArea.caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> controller.updateStatusLabel(textArea));

                // 更新编码标签
                controller.upDateEncodingLabel(textArea.getText());
            }
        });
    }

}
