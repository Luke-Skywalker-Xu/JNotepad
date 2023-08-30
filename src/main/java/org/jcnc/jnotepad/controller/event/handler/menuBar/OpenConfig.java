package org.jcnc.jnotepad.controller.event.handler.menuBar;

import javafx.event.ActionEvent;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.SingletonUtil;

import java.io.File;

/**
 * 打开配置文件事件
 *
 * @author gewuyou
 */
public class OpenConfig extends OpenFile {

    @Override
    public void handle(ActionEvent actionEvent) {
        // 显示文件选择对话框，并获取配置文件
        File file = SingletonUtil.getAppConfigController().getConfigPath().toFile();
        LogUtil.getLogger(this.getClass()).info("已调用打开配置文件功能,{}", file);
        // 创建打开文件的任务并启动线程执行任务
        openFile(file);
    }
}
