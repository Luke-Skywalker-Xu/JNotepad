package org.jcnc.jnotepad;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class JNotepad extends Application {
    String Title = "JNotepad";
    Label encodingLabel; // 新增属性用于显示文本编码

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
    boolean a = true;

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
        statusLabel = new Label("行: 1 \t列: 1 \t字数: 0 \t编码: ");
        encodingLabel = new Label(); // 创建新的标签用于显示编码信息
        HBox statusBox = new HBox(statusLabel, encodingLabel); // 使用 HBox 放置状态标签和编码标签
        root.setBottom(statusBox);
        BorderPane.setMargin(statusBox, new Insets(5, 10, 5, 10));


        List<String> rawParameters = getParameters().getRaw();

        //关联文件打开
        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath);
        }

        TextArea textArea = new TextArea(); // 创建新的文本编辑区

        // 添加文本变更监听器
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // 更新状态栏信息
            updateStatusLabel(textArea);
            // 更新编码信息
            //updateEncodingLabel(newValue);
            updateEncodingLabel(textArea.getText()); // 更新文本编码信息

        });
        autoSave(textArea); // 自动保存

        if (a) {
            Tab tab = new Tab("新建文件 " + ++tabIndex); // 创建新的Tab页
            tab.setContent(textArea);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            updateStatusLabel(textArea);

        }



        // 创建场景并设置主界面
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image((Objects.requireNonNull(getClass().getResource("/img/icon.png"))).toString()));

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
            // 更新编码信息
            //updateEncodingLabel(newValue);
            updateEncodingLabel(textArea.getText()); // 更新文本编码信息
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
                    getTXT(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void autoSave(TextArea textArea) {
        // 在创建文本编辑区后添加文本变更监听器
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            if (tab != null) {
                File f = (File) tab.getUserData();
                if (f != null) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                        writer.write(newValue); // 写入新的文本内容
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // 保存文件事件处理器
    private class SaveFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                File file = (File) selectedTab.getUserData(); // 获取当前Tab页对应的文件对象
                if (file == null) {
                    // If no file is associated (newly created tab), perform Save As logic
                    saveAsFile();
                } else {
                    // File is associated, proceed with regular save logic
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
                    autoSave(textArea);// 自动保存
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
            statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length+"\t编码: ");
        });
    }

    private void openAssociatedFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                a = false;
                getTXT(file);// 读取文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读取文件
    private void getTXT(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder textBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            textBuilder.append(line).append("\n"); // 读取文件内容
        }
        reader.close();
        String text = textBuilder.toString();

        TextArea textArea = new TextArea(text); // 创建新的文本编辑区
        autoSave(textArea); // 自动保存
        Tab tab = new Tab(file.getName()); // 创建新的Tab页

        tab.setContent(textArea);
        tab.setUserData(file); // 将文件对象保存到Tab页的UserData中

        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        updateStatusLabel(textArea);
    }

    private void updateEncodingLabel(String text) {
        String[] commonEncodings = {"UTF-8", "ISO-8859-1", "UTF-16", "US-ASCII"}; // 常见编码
        String detectedEncoding = ""; // 默认值

        for (String encoding : commonEncodings) {
            if (isEncodingValid(text, encoding)) {
                detectedEncoding = encoding;
                break;
            }
        }

        encodingLabel.setText(detectedEncoding);
    }

    private boolean isEncodingValid(String text, String encoding) {
        try {
            byte[] bytes = text.getBytes(encoding);
            String decodedText = new String(bytes, encoding);
            return text.equals(decodedText);
        } catch (Exception e) {
            return false;
        }
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
