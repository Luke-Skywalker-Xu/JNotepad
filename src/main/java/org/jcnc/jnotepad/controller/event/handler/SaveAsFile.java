package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.tool.LogUtil;

import static org.jcnc.jnotepad.tool.FileUtil.saveTab;


/**
 * 保存文件事件处理器。
 * <p>
 * 当用户选择保存文件时，当用户选择另存为文件菜单或按钮时，
 * 会弹出一个保存文件对话框，用户选择保存位置和文件名后，
 * 将当前文本编辑区的内容保存到指定文件中，
 * 并更新Tab页上的文件名和UserData。
 *
 * @author 许轲
 */
public class SaveAsFile implements EventHandler<ActionEvent> {
    /**
     * 处理保存文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        LogUtil.getLogger(SaveAsFile.class).info("已调用另存为功能");
        saveTab(this.getClass());
    }
}
