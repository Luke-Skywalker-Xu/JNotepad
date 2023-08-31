package org.jcnc.jnotepad;


import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
 * 启动程序
 *
 * @author 许轲
 */
public class LunchApp extends Application {
    /**
     * 线程池
     */
    private final ExecutorService threadPool = ThreadPoolManager.getThreadPool();
    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        double width = AppConstants.SCREEN_WIDTH;
        double length = AppConstants.SCREEN_LENGTH;
        scene = new Scene(root, width, length);
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        initUiComponents();
        UiResourceBundle.bindStringProperty(primaryStage.titleProperty(), TextConstants.TITLE);

/*        // 获取自定义标题栏的实例并添加到BorderPane的顶部
        CustomTitleBar customTitleBar = CustomTitleBar.getInstance();
        // 使自定义标题栏可拖动
        customTitleBar.makeDraggable(primaryStage);*/
//        primaryStage.initStyle(StageStyle.UNDECORATED); // 移除默认窗口装饰

        primaryStage.setScene(scene);
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(UiUtil.getIcon());
        primaryStage.show();
    }

    private void initUiComponents() {

        //1. 加载语言
        LocalizationController.initLocal();

        //2. 加载组件
        ViewManager viewManager = ViewManager.getInstance(scene);
        viewManager.initScreen(scene);

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
