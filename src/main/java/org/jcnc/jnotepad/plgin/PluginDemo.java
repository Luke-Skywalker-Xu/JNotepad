package org.jcnc.jnotepad.plgin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author luke
 */
public class PluginDemo  {

    public void start(Stage primaryStage) {
        PluginManager pluginManager = new PluginManager();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JAR Files", "*.jar")
        );

        Button loadButton = new Button("加载插件");
        loadButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                String pluginFilePath = selectedFile.getAbsolutePath();
                pluginManager.loadPlugins(pluginFilePath);

                // 更新插件信息显示
                displayPluginInfo(primaryStage, pluginManager);
            }
        });

        Button executeButton = new Button("执行插件");
        executeButton.setOnAction(event -> pluginManager.executePlugins());

        VBox root = new VBox(10, loadButton, executeButton);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("插件演示");
        primaryStage.show();
    }

    private void displayPluginInfo(Stage primaryStage, PluginManager pluginManager) {
        Map<String, List<String>> loadedPluginsByCategory = pluginManager.getLoadedPluginsByCategory();
        VBox infoBox = new VBox();

        for (String category : loadedPluginsByCategory.keySet()) {
            Label categoryLabel = new Label("类别: " + category);
            VBox categoryInfoBox = new VBox();
            List<String> pluginNames = loadedPluginsByCategory.get(category);
            for (String pluginName : pluginNames) {
                Label pluginLabel = new Label("插件名称: " + pluginName);
                categoryInfoBox.getChildren().add(pluginLabel);
            }
            infoBox.getChildren().addAll(categoryLabel, categoryInfoBox);
        }

        Scene infoScene = new Scene(infoBox, 400, 300);
        Stage infoStage = new Stage();
        infoStage.setScene(infoScene);
        infoStage.setTitle("已加载的插件");
        infoStage.initOwner(primaryStage);
        infoStage.show();
    }
}
