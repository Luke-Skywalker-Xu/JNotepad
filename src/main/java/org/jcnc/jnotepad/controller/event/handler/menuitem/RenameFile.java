package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.ui.views.manager.CenterTabPaneManager;

import static org.jcnc.jnotepad.app.util.TabUtil.rename;

/**
 * 重命名文件事件处理器。
 * <p>
 * 当用户选择重命名文件时，如果当前标签页关联文件，则重命名关联文件；
 * 否则，重命名标签页。
 *
 * @author gewuyou
 */
public class RenameFile implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        rename(CenterTabPaneManager.getInstance().getSelected());
    }
}
