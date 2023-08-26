package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.manager.ThreadPoolManager;
import org.jcnc.jnotepad.tool.LogUtil;

import java.io.File;

import static org.jcnc.jnotepad.constants.AppConstants.CONFIG_NAME;

/**
 * 打开配置文件事件
 *
 * @author gewuyou
 */
public class OpenConfig implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        Controller controller = Controller.getInstance();
        // 显示文件选择对话框，并获取配置文件
        File file = new File(CONFIG_NAME);
        LogUtil.getLogger(this.getClass()).info("已调用打开配置文件功能");
        LogUtil.getLogger(this.getClass()).info("{}", file);
        // 创建打开文件的任务并启动线程执行任务
        ThreadPoolManager.getThreadPool().submit(controller.createOpenFileTask(file));
    }
}
