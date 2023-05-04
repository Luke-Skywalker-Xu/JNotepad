import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class JNotepad extends Application {
    String iconSrc = "img/icon.png";

    String Title = "JNotepad";

    // 定义菜单栏
    MenuBar menuBar;
    Menu fileMenu;
    MenuItem newItem, openItem, saveItem, saveAsItem;

    // 定义主界面
    BorderPane root;

    // 定义多个Tab页
    TabPane tabPane;
    int tabIndex = 0;

    // 定义状态栏
    Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(Title);

        // 创建菜单栏并添加菜单项
        menuBar = new MenuBar();
        fileMenu = new Menu("文件");
        newItem = new MenuItem("新建");
        openItem = new MenuItem("打开");
        saveItem = new MenuItem("保存");
        saveAsItem = new MenuItem("另存为");
        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem);
        menuBar.getMenus().add(fileMenu);

        // 为菜单项添加事件处理器
        newItem.setOnAction(new NewFileEventHandler());
        openItem.setOnAction(new OpenFileEventHandler());
        saveItem.setOnAction(new SaveFileEventHandler());
        saveAsItem.setOnAction(new SaveAsFileEventHandler());

        // 创建主界面
        root = new BorderPane();
        root.setTop(menuBar);

        // 创建Tab页和文本编辑区
        tabPane = new TabPane();
        root.setCenter(tabPane);

        // 创建状态栏
        statusLabel = new Label("行: 1 \t列: 1 \t字数: 0");
        root.setBottom(statusLabel);
        BorderPane.setMargin(statusLabel, new Insets(5, 10, 5, 10));

        TextArea textArea = new TextArea(); // 创建新的文本编辑区
        Tab tab = new Tab("新建文件 " + ++tabIndex); // 创建新的Tab页
        tab.setContent(textArea);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        updateStatusLabel(textArea);

        // 创建场景并设置主界面
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(iconSrc));
        primaryStage.show();
    }

    // 新建文件事件处理器
    private class NewFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            TextArea textArea = new TextArea(); // 创建新的文本编辑区
            Tab tab = new Tab("新建文本 " + ++tabIndex); // 创建新的Tab页
            tab.setContent(textArea);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            updateStatusLabel(textArea);
        }
    }

    // 打开文件事件处理器
    private class OpenFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    StringBuilder textBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        textBuilder.append(line).append("\n"); // 读取文件内容
                    }
                    reader.close();
                    String text = textBuilder.toString();

                    TextArea textArea = new TextArea(text); // 创建新的文本编辑区
                    Tab tab = new Tab(file.getName()); // 创建新的Tab页
                    tab.setContent(textArea);
                    tab.setUserData(file); // 将文件对象保存到Tab页的UserData中
                    tabPane.getTabs().add(tab);
                    tabPane.getSelectionModel().select(tab);
                    updateStatusLabel(textArea);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 保存文件事件处理器
    private class SaveFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                File file = (File) selectedTab.getUserData(); // 获取当前Tab页对应的文件对象
                if (file.exists()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        TextArea textArea = (TextArea) selectedTab.getContent(); // 获取当前Tab页的文本编辑区
                        String text = textArea.getText();
                        writer.write(text); // 写入文件内容
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    saveAsFile();
                }
            }
        }
    }

    // 另存为文件事件处理器
    private class SaveAsFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            saveAsFile();
        }
    }

    // 另存为文件方法
    private void saveAsFile() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("新建文本");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    TextArea textArea = (TextArea) selectedTab.getContent(); // 获取当前Tab页的文本编辑区
                    String text = textArea.getText();
                    writer.write(text); // 写入文件内容
                    writer.flush();
                    writer.close();
                    selectedTab.setText(file.getName()); // 更新Tab页标签上的文件名
                    selectedTab.setUserData(file); // 将文件对象保存到Tab页的UserData中
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 更新状态栏
    private void updateStatusLabel(TextArea textArea) {
        textArea.caretPositionProperty().addListener((observable, oldValue, newValue) -> {
            int row = getRow(textArea.getCaretPosition(), textArea.getText());
            int column = getColumn(textArea.getCaretPosition(), textArea.getText());
            int length = textArea.getLength();
            statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length);
        });
    }

    // 获取光标所在行数
    private int getRow(int caretPosition, String text) {
        return text.substring(0, caretPosition).split("\n").length;
    }


    // 获取光标所在列数
    private int getColumn(int caretPosition, String text) {
        return caretPosition - text.lastIndexOf("\n", caretPosition - 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}