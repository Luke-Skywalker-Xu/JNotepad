package org.jcnc.jnotepad.view;

import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.controller.Controller;

import static org.jcnc.jnotepad.ViewManager.*;
import static org.jcnc.jnotepad.controller.Controller.updateStatusLabel;

public class view {

    public static void initItem() {
        // 为菜单项添加事件处理器
        newItem.setOnAction(new Controller.NewFileEventHandler());
        openItem.setOnAction(new Controller.OpenFileEventHandler());
        saveItem.setOnAction(new Controller.SaveFileEventHandler());
        saveAsItem.setOnAction(new Controller.SaveAsFileEventHandler());
    }

    public static void initTabPane() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                TextArea textArea = (TextArea) newTab.getContent();
                updateStatusLabel(textArea);

                // Update status label
                textArea.caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> updateStatusLabel(textArea));

                // Update encoding label
                Controller.updateEncodingLabel(textArea.getText());
            }
        });
    }
}
