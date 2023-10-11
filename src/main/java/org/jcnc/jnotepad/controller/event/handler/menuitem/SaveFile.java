package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.ui.views.manager.CenterTabPaneManager;

import static org.jcnc.jnotepad.app.utils.TabUtil.saveFile;

/**
 * 保存文件事件处理程序。
 * <p>
 * 当用户选择保存文件时，如果当前标签页是关联文件，则自动保存；
 * 否则，调用另存为方法。
 *
 * @author gewuyou
 */
public class SaveFile implements EventHandler<ActionEvent> {

    /**
     * 处理保存文件事件。
     *
     * @param actionEvent 事件对象
     * @apiNote 当用户选择保存文件时，如果当前标签页是关联文件，则自动保存；
     * 否则，调用另存为方法。
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // 保存当前选中的标签页
        saveFile(CenterTabPaneManager.getInstance().getSelected());
    }
}
