package org.jcnc.jnotepad.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.MainApp;

import java.io.*;
import java.util.List;

import static org.jcnc.jnotepad.ViewManager.*;

/**
 * 控制器类负责处理与用户界面的交互，并实现相关事件处理逻辑。
 */
public class Controller {

    /**
     * 打开关联文件并创建一个文本编辑区。
     *
     * @param rawParameters 文件路径参数的列表
     * @return 创建的文本编辑区
     */
    public static TextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters) {
        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath); //// 调用关联文件打开方法
        }

        TextArea textArea = new TextArea(); // 创建新的文本编辑区

        upDateEncodingLabel(textArea.getText()); // 更新文本编码信息
        updateStatusLabel(textArea);

        // 添加文本变更监听器
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // 更新状态栏信息
            updateStatusLabel(textArea);
        });
        autoSave(textArea); // 自动保存
        return textArea;
    }

    // 新建文件事件处理器
    public static class NewFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            TextArea textArea = new TextArea(); // 创建新的文本编辑区
            Tab tab = new Tab("新建文本 " + ++tabIndex); // 创建新的Tab页
            tab.setContent(textArea);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            updateStatusLabel(textArea);
            // 更新编码信息
            upDateEncodingLabel(textArea.getText()); // 更新文本编码信息
        }
    }

    // 打开文件事件处理器
    public static class OpenFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                Task<Void> openFileTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        getText(file);
                        upDateEncodingLabel(((TextArea) tabPane.getSelectionModel().getSelectedItem().getContent()).getText());
                        return null;
                    }
                };

                openFileTask.setOnSucceeded(e -> {
                    // 在任务完成后的操作（更新界面等）
                });

                openFileTask.setOnFailed(e -> {
                    // 在任务失败时的操作（处理异常等）
                });

                Thread thread = new Thread(openFileTask);
                thread.start();
            }
        }
    }

    // 自动保存方法
    public static void autoSave(TextArea textArea) {
        // 当文本编辑区内容发生变化时，自动保存文本到文件
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
                    } catch (IOException ignored) {
                        // 处理异常，忽略
                    }
                }
            }
        });
    }

    // 保存文件事件处理器
    public static class SaveFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                File file = (File) selectedTab.getUserData(); // 获取当前Tab页对应的文件对象
                if (file == null) {
                    // 如果没有关联文件(新创建的选项卡)，执行另存为逻辑
                    saveAsFile();
                } else {
                    // 文件已关联，继续使用常规保存逻辑
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        TextArea textArea = (TextArea) selectedTab.getContent(); // 获取当前Tab页的文本编辑区
                        String text = textArea.getText();
                        writer.write(text); // 写入文件内容
                        writer.flush();
                        writer.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    // 另存为文件事件处理器
    public static class SaveAsFileEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            saveAsFile();
        }
    }

    // 另存为文件方法
    public static void saveAsFile() {
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
                } catch (IOException ignored) {
                }
            }
        }
    }

    // 更新状态栏标签信息
    public static void updateStatusLabel(TextArea textArea) {
        int caretPosition = textArea.getCaretPosition();
        int row = getRow(caretPosition, textArea.getText());
        int column = getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length);
        System.out.println("        正在监测字数");
    }

    // 关联文件打开
    public static void openAssociatedFile(String filePath) {
        // 根据给定的文件路径打开关联文件
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                MainApp.isRelevance = false;
                getText(file);// 调用读取文件方法
                upDateEncodingLabel(((TextArea) tabPane.getSelectionModel().getSelectedItem().getContent()).getText()); // 更新文本编码信息
            } catch (IOException ignored) {
                // 处理异常，忽略
            }
        }
    }

    // 读取文件并创建文本编辑区
    public static void getText(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder textBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            textBuilder.append(line).append("\n");  // 逐行读取文件内容
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
    // 更新文本编码标签信息
    public static void upDateEncodingLabel(String text) {
        String encoding = detectEncoding(text);
        enCodingLabel.setText("\t编码: " + encoding);
    }
    // 判断编码是否有效
    public static boolean isEncodingValid(String text, String encoding) {
        // 编码有效性检查
        // 使用指定的编码解码文本，并检查是否出现异常来判断编码是否有效
        try {
            byte[] bytes = text.getBytes(encoding);
            String decodedText = new String(bytes, encoding);
            return text.equals(decodedText);
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }
    // 检测文本编码
    public static String detectEncoding(String text) {
        // 使用不同的编码（如UTF-8、ISO-8859-1等）来解码文本，并检查是否出现异常来判断编码
        String[] possibleEncodings = {"UTF-8", "ISO-8859-1", "UTF-16"};
        for (String encoding : possibleEncodings) {
            if (isEncodingValid(text, encoding)) {
                System.out.println("正在检测编码");
                return encoding;
            }
        }
        return "未知";
    }

    // 获取光标所在行数
    public static int getRow(int caretPosition, String text) {
        return text.substring(0, caretPosition).split("\n").length;
    }

    // 获取光标所在列数
    public static int getColumn(int caretPosition, String text) {
        return caretPosition - text.lastIndexOf("\n", caretPosition - 1);
    }
}
