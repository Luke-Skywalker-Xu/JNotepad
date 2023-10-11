package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static org.jcnc.jnotepad.app.utils.TabUtil.addNewFileTab;

/**
 * 新建文件事件的事件处理程序。
 *
 * <p>当用户选择新建文件时，将创建一个新的文本编辑区，并在Tab页中显示。</p>
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
        addNewFileTab();
    }
}
