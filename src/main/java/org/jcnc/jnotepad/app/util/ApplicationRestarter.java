package org.jcnc.jnotepad.app.util;

import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.common.util.LogUtil;

import java.io.IOException;

/**
 * @author luke
 */
public class ApplicationRestarter {
    private ApplicationRestarter() {
    }

    public static void restart() {
        try {
            // 获取当前Java应用程序的命令
            String javaCommand = System.getProperty("java.home") + "/bin/java";
            String mainClass = LunchApp.class.getName();

            // 构建新进程来重新启动应用程序
            ProcessBuilder builder = new ProcessBuilder(javaCommand, "-cp", System.getProperty("java.class.path"), mainClass);
            builder.start();

            // 关闭当前应用程序
            System.exit(0);
        } catch (IOException e) {
            LogUtil.getLogger("正在重启当前应用程序".getClass());

        }
    }
}