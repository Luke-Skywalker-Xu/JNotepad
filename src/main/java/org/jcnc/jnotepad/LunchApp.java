package org.jcnc.jnotepad;


import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.i18n.UIResourceBundle;
import org.jcnc.jnotepad.constants.AppConstants;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.manager.ThreadPoolManager;
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
    Controller controller = Controller.getInstance();
    Scene scene;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        double width = AppConstants.SCREEN_WIDTH;
        double length = AppConstants.SCREEN_LENGTH;
        String icon = AppConstants.APP_ICON;
        scene = new Scene(root, width, length);
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());
        initUiComponents();
        UIResourceBundle.bindStringProperty(primaryStage.titleProperty(), TextConstants.TITLE);
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(icon)).toString()));
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
        controller.openAssociatedFileAndCreateTextArea(rawParameters);
    }

    @Override
    public void stop() {
        // 关闭线程池
        threadPool.shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
