package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.util.LogUtil;

import java.io.File;

/**
 * 打开配置文件事件处理程序。
 *
 * <p>该事件处理程序用于打开配置文件。</p>
 *
 * @author gewuyou
 */
public class OpenConfig extends OpenFile {

    /**
     * 处理打开配置文件事件。
     *
     * @param actionEvent 事件对象
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // 显示文件选择对话框，并获取配置文件
        File file = UserConfigController.getInstance().getConfigPath().toFile();
        LogUtil.getLogger(this.getClass()).info("已调用打开配置文件功能, {}", file);
        // 创建打开文件的任务并启动线程执行任务
        openFile(file);
    }
}
