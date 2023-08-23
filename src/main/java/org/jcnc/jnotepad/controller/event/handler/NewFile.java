package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;
import org.jcnc.jnotepad.view.manager.ViewManager;


/**
 * 新建文件事件的事件处理程序。
 * <p>
 * 当用户选择新建文件时候,将创建一个新的文本编辑区，并在Tab页中显示。
 *
 * @author 许轲
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
        Controller controller = Controller.getInstance();

        // 创建一个新的文本编辑区
        LineNumberTextArea textArea = new LineNumberTextArea();

        textArea.setStyle(
                "-fx-border-color:white ;-fx-background-color:white;"
        );

        // TODO: refactor：统一TextArea新建、绑定监听器入口
        // 增加autoSave监听器绑定
        controller.autoSave(textArea);
        ViewManager viewManager = ViewManager.getInstance();
        // 将Tab页添加到TabPane中
        JNotepadTabPane.getInstance().addNewTab(new JNotepadTab("新建文本 "
                + viewManager.selfIncreaseAndGetTabIndex(),
                textArea));

        // 更新状态标签
        controller.updateStatusLabel(textArea);

        // 更新编码信息
        controller.upDateEncodingLabel(textArea.getMainTextArea().getText());
    }
}