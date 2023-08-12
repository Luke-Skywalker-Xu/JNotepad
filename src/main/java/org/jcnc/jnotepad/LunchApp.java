package org.jcnc.jnotepad;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.constants.Constants;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.jcnc.jnotepad.view.init.View.initItem;
import static org.jcnc.jnotepad.view.init.View.initTabPane;

public class LunchApp extends Application {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static boolean isRelevance = true;

    Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        double width = Constants.SCREEN_WIDTH;
        double length = Constants.SCREEN_LENGTH;
        String name = Constants.APP_NAME;
        String icon = Constants.APP_ICON;

        Scene scene = new Scene(root, width, length);

        primaryStage.setTitle(name);
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(icon)).toString()));
        primaryStage.show();

        ViewManager viewManager = ViewManager.getInstance(scene);
        viewManager.initScreen(scene);

        // 初始化菜单项和标签栏
        initItem();
        initTabPane();

        if (isRelevance) {
            // 使用线程池加载关联文件并创建文本区域
            List<String> rawParameters = getParameters().getRaw();
            threadPool.execute(() -> {
                TextArea textArea = controller.openAssociatedFileAndCreateTextArea(rawParameters);
                Platform.runLater(() -> updateUIWithNewTextArea(textArea));
            });
        }
    }

    private void updateUIWithNewTextArea(TextArea textArea) {
        Tab tab = new Tab("新建文件 " + (++ViewManager.tabIndex));
        tab.setContent(textArea);
        ViewManager.tabPane.getTabs().add(tab);
        ViewManager.tabPane.getSelectionModel().select(tab);
        controller.updateStatusLabel(textArea);
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
