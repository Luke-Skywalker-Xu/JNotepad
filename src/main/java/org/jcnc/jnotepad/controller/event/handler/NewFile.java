package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.view.manager.ViewManager;

import static org.jcnc.jnotepad.view.manager.ViewManager.tabPane;

/**
 *处理新建文件事件的事件处理程序。
 *当用户选择新建文件菜单或按钮时，将创建一个新的文本编辑区，并在Tab页中显示。
 * */


public class NewFile implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        Controller controller = new Controller();
        TextArea textArea = new TextArea(); // 创建新的文本编辑区
        Tab tab = new Tab("新建文本 " + ++ViewManager.tabIndex); // 创建新的Tab页
        tab.setContent(textArea);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        controller.updateStatusLabel(textArea);
        // 更新编码信息
        controller.upDateEncodingLabel(textArea.getText()); // 更新文本编码信息
    }
}