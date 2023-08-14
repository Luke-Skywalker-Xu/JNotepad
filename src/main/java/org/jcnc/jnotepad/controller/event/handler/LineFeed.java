package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
/**
 * LineFeed 类是一个事件处理程序，用于在文本区域中插入一个换行符。
 */

public class LineFeed implements EventHandler<ActionEvent> {
    private final TextArea textArea;

    /**
     * 构造函数，初始化 LineFeed 对象。
     * @param textArea 要操作的文本区域
     */
    public LineFeed(TextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * 处理事件的方法，将一个换行符插入到文本区域的末尾。
     * @param event 触发的事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        String text = textArea.getText();
        textArea.setText(text + "\n");
    }
}
