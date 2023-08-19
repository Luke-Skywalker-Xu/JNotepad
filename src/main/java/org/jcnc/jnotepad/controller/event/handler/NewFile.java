package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.Objects;

import static org.jcnc.jnotepad.view.manager.ViewManager.tabPane;

/**
 * 新建文件事件的事件处理程序。
 * <p>
 * 当用户选择新建文件时候,将创建一个新的文本编辑区，并在Tab页中显示。
 */
public class NewFile implements EventHandler<ActionEvent> {
    /**
     * 处理新建文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        // 获取控制器
        Controller controller = new Controller();

        LineNumberTextArea lineNumberTextArea = new LineNumberTextArea();


        // 创建一个新的文本编辑区
        LineNumberTextArea textArea = new LineNumberTextArea();


        textArea.setStyle(
                "-fx-border-color:white ;-fx-background-color:white;"
        );


        // 创建一个新的Tab页
        Tab tab = new Tab("新建文本 " + ++ViewManager.tabIndex);
        tab.setContent(lineNumberTextArea.getMainTextArea());

        // 将Tab页添加到TabPane中
        tabPane.getTabs().add(tab);

        // 将新建的Tab页设置为选中状态
        tabPane.getSelectionModel().select(tab);

        // 更新状态标签
        controller.updateStatusLabel(lineNumberTextArea);

        // 更新编码信息
        controller.upDateEncodingLabel(lineNumberTextArea.getMainTextArea().getText());
    }
}