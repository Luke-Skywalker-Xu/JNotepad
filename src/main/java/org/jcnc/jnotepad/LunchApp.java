package org.jcnc.jnotepad;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.constants.Constants;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.view.init.View;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LunchApp extends Application {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static boolean isRelevance = true;

    Controller controller = Controller.getInstance();
    Scene scene;
    View view;

    @Override
    public void start(Stage primaryStage) {

        view = new View();

        Pane root = new Pane();


        double width = Constants.SCREEN_WIDTH;
        double length = Constants.SCREEN_LENGTH;
        String name = Constants.APP_NAME;
        String icon = Constants.APP_ICON;

        scene = new Scene(root, width, length);
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        primaryStage.setTitle(name);
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(icon)).toString()));
        primaryStage.show();
        ViewManager viewManager = ViewManager.getInstance(scene);
        viewManager.initScreen(scene);

        // 初始化菜单项和标签栏
        view.initItem();
        view.initTabPane();
        view.initShortcutKey();

        if (isRelevance) {

            // 使用线程池加载关联文件并创建文本区域
            List<String> rawParameters = getParameters().getRaw();
            threadPool.execute(() -> {
                LineNumberTextArea textArea = controller.openAssociatedFileAndCreateTextArea(rawParameters);
                if (!Objects.isNull(textArea)) {
                    Platform.runLater(() -> controller.updateUIWithNewTextArea(textArea));
                }
            });
        }

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
