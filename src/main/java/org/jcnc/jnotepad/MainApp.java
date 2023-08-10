package org.jcnc.jnotepad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.controller.controller;
import org.jcnc.jnotepad.view.view;

import java.util.List;
import java.util.Objects;

import static org.jcnc.jnotepad.ViewManager.tabPane;
import static org.jcnc.jnotepad.controller.controller.updateStatusLabel;

/**
 * 启动器
 */

public class MainApp extends Application {
    public static boolean isRelevance = true;

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        double width = Constants.SCREEN_WIDTH;
        double length = Constants.SCREEN_LENGTH;
        String name = Constants.APP_NAME;
        String icon =Constants.APP_ICON;

        Scene scene = new Scene(root, width, length);

        primaryStage.setTitle(name);
        primaryStage.setWidth(width);
        primaryStage.setHeight(length);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image((Objects.requireNonNull(getClass().getResource(icon))).toString()));
        primaryStage.show();

        ViewManager viewManager = ViewManager.getInstance(scene);

        viewManager.initScreen(scene);

        initApp();


    }

    private void initApp() {
        List<String> rawParameters = getParameters().getRaw();

        TextArea textArea = controller.openRelevance(rawParameters);
        if (isRelevance) {
            Tab tab = new Tab("新建文件 " + ++ViewManager.tabIndex); // 创建新的Tab页
            tab.setContent(textArea);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            updateStatusLabel(textArea);
        }
        view.initItem();
        view.initTabPane();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
