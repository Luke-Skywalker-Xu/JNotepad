package org.jcnc.jnotepad;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.constants.AppConstants;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.init.Config;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.view.init.View;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启动程序
 *
 * @author 许轲
 */
public class LunchApp extends Application {
    /**
     * 线程池
     */
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    Controller controller = Controller.getInstance();
    Scene scene;
    View view;

    @Override
    public void start(Stage primaryStage) {

        Config config = new Config();

        Properties properties = config.readPropertiesFromFile();
        String title = properties.getProperty("title", "JNotepad");


        view = new View();

        Pane root = new Pane();


        double width = AppConstants.SCREEN_WIDTH;
        double length = AppConstants.SCREEN_LENGTH;
        String icon = AppConstants.APP_ICON;



        scene = new Scene(root, width, length);
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm());

        // 使用线程池加载关联文件并创建文本区域
        List<String> rawParameters = getParameters().getRaw();
        THREAD_POOL.execute(() -> {
            LineNumberTextArea textArea = controller.openAssociatedFileAndCreateTextArea(rawParameters);
            if (!Objects.isNull(textArea)) {
                Platform.runLater(() -> controller.updateUiWithNewTextArea(textArea));
            }
        });
        primaryStage.setTitle(title);
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(icon)).toString()));
        primaryStage.show();
        ViewManager viewManager = ViewManager.getInstance(scene);
        viewManager.initScreen(scene);
        // 初始化快捷键
        view.initShortcutKey();


    }

    @Override
    public void stop() {
        // 关闭线程池
        THREAD_POOL.shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
