package org.jcnc.jnotepad.controller.event.handler.toolbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.views.manager.DirectorySidebarManager;


/**
 * 文件树按钮
 *
 * <p>文件树按钮事件的事件处理程序。</p>
 *
 * @author cccqyu
 */
public class DirTreeBtn implements EventHandler<ActionEvent> {

    private static final DirectorySidebarManager DIRECTORY_SIDEBAR_MANAGER = DirectorySidebarManager.getInstance();

    @Override
    public void handle(ActionEvent actionEvent) {
        DIRECTORY_SIDEBAR_MANAGER.controlShow();
    }
}
