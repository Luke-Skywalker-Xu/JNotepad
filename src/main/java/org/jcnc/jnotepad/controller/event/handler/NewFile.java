package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.component.JTab;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.view.manager.ViewManager;

import static org.jcnc.jnotepad.view.manager.ViewManager.tabPane;

public class NewFile implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        Controller controller = new Controller();
        TextArea textArea = new TextArea(); // 创建新的文本编辑区
        JTab tab = new JTab("新建文本 " + ++ViewManager.tabIndex,textArea); // 创建新的Tab页
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        // 更新编码信息
        controller.upDateEncodingLabel(textArea.getText()); // 更新文本编码信息
    }
}