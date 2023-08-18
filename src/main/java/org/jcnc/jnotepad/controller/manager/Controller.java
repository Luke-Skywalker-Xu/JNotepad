package org.jcnc.jnotepad.controller.manager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.Interface.ControllerInterface;
import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.controller.event.handler.LineFeed;
import org.jcnc.jnotepad.controller.event.handler.NewFile;
import org.jcnc.jnotepad.controller.event.handler.OpenFile;
import org.jcnc.jnotepad.controller.event.handler.SaveAsFile;
import org.jcnc.jnotepad.tool.EncodingDetector;
import org.jcnc.jnotepad.view.manager.ViewManager;
import org.jcnc.jnotepad.component.JTab;
import org.jcnc.jnotepad.controller.event.handler.*;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.io.*;
import java.util.List;

/**
 * 控制器类，实现ControllerInterface接口，用于管理文本编辑器的各种操作和事件处理。
 * 包括打开关联文件、创建文本区域、处理行分隔、新建文件、打开文件、自动保存等功能。
 */
public class Controller implements ControllerInterface {

    /**
     * 打开关联文件并创建文本区域。
     *
     * @param rawParameters 原始参数列表
     * @return 创建的文本区域
     */
    @Override
    public TextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters) {
        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath);
            return null;
        } else {
            TextArea textArea = createNewTextArea();
            return textArea;
        }
    }

    /**
     * 获取行分隔事件处理程序。
     *
     * @param textArea 文本区域
     * @return 行分隔事件处理程序
     */
    @Override
    public EventHandler<ActionEvent> getLineFeedEventHandler(TextArea textArea) {
        return new LineFeed(textArea);
    }

    /**
     * 获取新建文件事件处理程序。
     *
     * @param textArea 文本区域
     * @return 新建文件事件处理程序
     */
    @Override
    public EventHandler<ActionEvent> getNewFileEventHandler(TextArea textArea) {
        return new NewFile();
    }

    /**
     * 获取打开文件事件处理程序。
     *
     * @return 打开文件事件处理程序
     */
    @Override
    public EventHandler<ActionEvent> getOpenFileEventHandler() {
        return new OpenFile();
    }

    /**
     * 自动保存文本内容。
     *
     * @param textArea 文本区域
     */
    @Override
    public void autoSave(TextArea textArea) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            JTab tab = (JTab) ViewManager.tabPane.getSelectionModel().getSelectedItem();
            if (tab != null) {
                File file = (File) tab.getUserData();
                if (file != null) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(newValue);
                        System.out.println("正在自动保存---");
                    } catch (IOException ignored) {

                    }
                }
            }
        });
    }

    /**
     * 获取另存为文件事件处理程序。
     *
     * @return 另存为文件事件处理程序
     */
    @Override
    public EventHandler<ActionEvent> getSaveAsFileEventHandler() {
        return new SaveAsFile();
    }

    /**
     * 更新状态标签。
     *
     * @param textArea 文本区域
     */
    @Override
    public void updateStatusLabel(TextArea textArea) {
        int caretPosition = textArea.getCaretPosition();
        int row = getRow(caretPosition, textArea.getText());
        int column = getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        ViewManager.statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length);
    }

    /**
     * 打开关联文件。
     *
     * @param filePath 文件路径
     */
    @Override
    public void openAssociatedFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            LunchApp.isRelevance = false;
            openFile(file);
        }
    }

    /**
     * 读取文本文件的内容。
     *
     * @param file 文件对象
     */
    @Override
    public void getText(File file) {
        TextArea textArea = createNewTextArea();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line).append("\n");
            }
            String text = textBuilder.toString();

            Platform.runLater(() -> {
                textArea.setText(text);

                JTab tab = new JTab(file.getName(), textArea);
                tab.setUserData(file);
                ViewManager.tabPane.getTabs().add(tab);
                ViewManager.tabPane.getSelectionModel().select(tab);
                autoSave(textArea);
            });
        } catch (IOException ignored) {

        }
    }

    /**
     * 更新编码标签。
     *
     * @param text 文本内容
     */
    @Override
    public void upDateEncodingLabel(String text) {
        String encoding = EncodingDetector.detectEncoding(text);
        ViewManager.enCodingLabel.setText("\t编码: " + encoding);
    }

    /**
     * 获取光标所在行号。
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     * @return 光标所在行号
     */
    @Override
    public int getRow(int caretPosition, String text) {
        caretPosition = Math.min(caretPosition, text.length());
        String substring = text.substring(0, caretPosition);
        int count = 0;
        for (char c : substring.toCharArray()) {
            if (c == '\n') {
                count++;
            }
        }
        return count + 1;
    }

    /**
     * 获取光标所在列号。
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     * @return 光标所在列号
     */
    @Override
    public int getColumn(int caretPosition, String text) {
        return caretPosition - text.lastIndexOf("\n", caretPosition - 1);
    }

    /**
     * 初始化标签面板。
     */
    @Override
    public void initTabPane() {
        Controller controller = new Controller();

        ViewManager.tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                JTab jtab = (JTab) newTab;
                // 获取新选定的标签页并关联的文本区域
                ScrollPane scrollPane = (ScrollPane) jtab.getContent();
                HBox hBox = (HBox) scrollPane.getContent();
                TextArea textArea = (TextArea) hBox.getChildren().get(1);
                // 监听文本光标位置的变化，更新状态标签
                textArea.caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> jtab.updateRowAndColumn());
                // 更新编码标签
                controller.upDateEncodingLabel(textArea.getText());
            }
        });
    }

    @Override
    public void updateUIWithNewTextArea(TextArea textArea) {
        JTab tab = new JTab("新建文件 " + (++ViewManager.tabIndex));
        tab.init(textArea);
        ViewManager.tabPane.getTabs().add(tab);
        ViewManager.tabPane.getSelectionModel().select(tab);
        updateStatusLabel(textArea);
    }

    /**
     * 配置文本区域。
     *
     * @param textArea 文本区域
     */
    public void configureTextArea(TextArea textArea) {
        textArea.setWrapText(true);
        upDateEncodingLabel(textArea.getText());
        autoSave(textArea);
    }

    /**
     * 创建新的文本区域。
     *
     * @return 新的文本区域
     */
    private TextArea createNewTextArea() {
        return new TextArea();
    }

    /**
     * 创建新的标签页。
     *
     * @param tabName  标签名
     * @param textArea 文本区域
     * @return 新的标签页
     */
    private JTab createNewTab(String tabName, TextArea textArea) {
        JTab tab = new JTab(tabName);
        tab.setContent(textArea);
        tab.setUserData(null);
        return tab;
    }

    /**
     * 创建打开文件的任务。
     *
     * @param file 文件对象
     * @return 打开文件的任务
     */
    private Task<Void> createOpenFileTask(File file) {
        TextArea textArea = createNewTextArea();
        return new Task<>() {
            @Override
            protected Void call() {
                getText(file);
                upDateEncodingLabel(textArea.getText());
                return null;
            }
        };
    }

    /**
     * 打开文件。
     *
     * @param file 文件对象
     */
    private void openFile(File file) {
        Task<Void> openFileTask = createOpenFileTask(file);
        Thread thread = new Thread(openFileTask);
        thread.start();
    }

}
