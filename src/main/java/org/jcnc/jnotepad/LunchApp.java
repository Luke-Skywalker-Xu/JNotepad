package org.jcnc.jnotepad;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.constants.AppConstants;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.manager.ThreadPoolManager;
import org.jcnc.jnotepad.tool.SingletonUtil;
import org.jcnc.jnotepad.tool.UiUtil;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 启动程序类
 *
 * <p>该类用于启动 JNotepad 记事本应用程序。</p>
 *
 * @author 许轲
 */
public class LunchApp extends Application {

    private static final Pane ROOT = new Pane();
    private static final Scene SCENE;

    static {
        double width = AppConstants.SCREEN_WIDTH;
        double length = AppConstants.SCREEN_LENGTH;
        SCENE = new Scene(ROOT, width, length);
    }

    /**
     * 线程池
     */
    private final ExecutorService threadPool = ThreadPoolManager.getThreadPool();

    /**
     * 应用程序的入口点，启动 JavaFX 应用程序。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 获取当前窗口。
     *
     * @return 当前窗口
     */
    public static Window getWindow() {
        return SCENE.getWindow();
    }

    @Override
    public void start(Stage primaryStage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        SCENE.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        initUiComponents();
        UiResourceBundle.bindStringProperty(primaryStage.titleProperty(), TextConstants.TITLE);

        primaryStage.setScene(SCENE);
        primaryStage.setWidth(SCENE.getWidth());
        primaryStage.setHeight(SCENE.getHeight());
        primaryStage.getIcons().add(UiUtil.getIcon());
        primaryStage.show();
    }

    private void initUiComponents() {

        // 1. 加载语言
        LocalizationController.initLocal();

        // 2. 加载组件
        ViewManager viewManager = ViewManager.getInstance(SCENE);
        viewManager.initScreen(SCENE);

        // 使用线程池加载关联文件并创建文本区域
        List<String> rawParameters = getParameters().getRaw();
        SingletonUtil.getController().openAssociatedFileAndCreateTextArea(rawParameters);
    }

    @Override
    public void stop() {
        // 关闭线程池
        threadPool.shutdownNow();
    }
}
