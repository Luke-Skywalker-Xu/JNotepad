package org.jcnc.jnotepad;


import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.config.LocalizationConfig;
import org.jcnc.jnotepad.app.init.LoadJnotepadConfig;
import org.jcnc.jnotepad.app.init.LoadLanguageConfig;
import org.jcnc.jnotepad.app.init.LoadShortcutKeyConfig;
import org.jcnc.jnotepad.constants.AppConstants;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.view.init.View;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    LocalizationConfig localizationConfig = LocalizationConfig.getLocalizationConfig();
    Controller controller = Controller.getInstance();
    Scene scene;
    /**
     * 配置文件数组
     */
    private static final List<LoadJnotepadConfig<?>> LOAD_JNOTEPAD_CONFIGS = new ArrayList<>();

    static {
        // 语言配置文件
        LOAD_JNOTEPAD_CONFIGS.add(new LoadLanguageConfig());
        // 快捷键配置文件
        LOAD_JNOTEPAD_CONFIGS.add(new LoadShortcutKeyConfig());
    }

    /**
     * 获取配置文件数组
     *
     * @since 2023/8/26 16:05
     */
    public static List<LoadJnotepadConfig<?>> getLocalizationConfigs() {
        return LOAD_JNOTEPAD_CONFIGS;
    }

    @Override
    public void start(Stage primaryStage) {
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
        ViewManager viewManager = ViewManager.getInstance(scene);
        viewManager.initScreen(scene);
        // 加载配置文件
        View.getInstance().initJnotepadConfigs(LOAD_JNOTEPAD_CONFIGS);
        primaryStage.setTitle(localizationConfig.getTitle());
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(icon)).toString()));
        primaryStage.show();
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
