package org.jcnc.jnotepad;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.manager.ApplicationManager;
import org.jcnc.jnotepad.app.utils.LogUtil;


/**
 * 启动程序类
 *
 * <p>该类用于启动 JNotepad 记事本应用程序。</p>
 *
 * @author 许轲
 */
public class JnotepadApp extends Application {
    private static final ApplicationManager APPLICATION_MANAGER = ApplicationManager.getInstance();

    /**
     * 应用程序的入口点，启动 JavaFX 应用程序。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        // 获取当前启动位置a
        String currentWorkingDirectory = System.getProperty("user.dir");
        LogUtil.getLogger(this.getClass()).info("当前启动位置：{}", currentWorkingDirectory);
        // 设置参数
        APPLICATION_MANAGER.setApplication(this);
    }

    @Override
    public void start(Stage primaryStage) {
        APPLICATION_MANAGER.setPrimaryStage(primaryStage);
        // 加载应用程序资源
        APPLICATION_MANAGER.loadAppResources();
        // 加载应用程序缓存
        APPLICATION_MANAGER.loadAppCache();
        // 初始化应用程序
        APPLICATION_MANAGER.initializeApp();
        // 初始化默认操作
        APPLICATION_MANAGER.executeDefaultAction();
        primaryStage.show();
    }

    @Override
    public void stop() {
        APPLICATION_MANAGER.operationBeforeStopping();
    }
}