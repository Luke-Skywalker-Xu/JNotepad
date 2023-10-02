package org.jcnc.jnotepad.controller.event.handler.setting;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.views.manager.DirectorySidebarManager;

/**
 * @author : cccqyu
 * @createTime 2023/10/2  17:24
 * @description 文件树按钮事件的事件处理程序。
 */
public class DirTreeBtn implements EventHandler<ActionEvent> {

    private static final DirectorySidebarManager  DIRECTORY_SIDEBAR_MANAGER = DirectorySidebarManager.getInstance();

    @Override
    public void handle(ActionEvent actionEvent) {
        DIRECTORY_SIDEBAR_MANAGER.controlShow();
    }
}
