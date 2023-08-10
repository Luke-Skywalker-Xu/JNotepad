package org.jcnc.jnotepad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.controller.Controller;
import org.jcnc.jnotepad.view.View;

import java.util.List;
import java.util.Objects;

import static org.jcnc.jnotepad.viewManager.tabPane;
import static org.jcnc.jnotepad.controller.Controller.updateStatusLabel;

public class MainApp extends Application {
    public static boolean isRelevance = true;

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
        primaryStage.getIcons().add(new Image((Objects.requireNonNull(getClass().getResource(icon))).toString()));
        primaryStage.show();

        viewManager viewManager = org.jcnc.jnotepad.viewManager.getInstance(scene);

        viewManager.initScreen(scene);

        // 初始化应用程序
        initApp();
    }

    private void initApp() {
        List<String> rawParameters = getParameters().getRaw();

        // 打开关联文件并创建文本区域
        TextArea textArea = Controller.openAssociatedFileAndCreateTextArea(rawParameters);
        if (isRelevance) {
            // 创建新标签页并添加到标签栏
            Tab tab = new Tab("新建文件 " + ++viewManager.tabIndex);
            tab.setContent(textArea);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            updateStatusLabel(textArea);
        }
        // 初始化菜单项和标签栏
        View.initItem();
        View.initTabPane();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
