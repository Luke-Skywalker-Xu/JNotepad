package org.jcnc.jnotepad.controller.manager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;

import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.Interface.ControllerInterface;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.controller.event.handler.LineFeed;
import org.jcnc.jnotepad.controller.event.handler.NewFile;
import org.jcnc.jnotepad.controller.event.handler.OpenFile;
import org.jcnc.jnotepad.controller.event.handler.SaveAsFile;
import org.jcnc.jnotepad.tool.EncodingDetector;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.io.*;
import java.util.List;

/**
 * 控制器类，实现ControllerInterface接口，用于管理文本编辑器的各种操作和事件处理。
 * 包括打开关联文件、创建文本区域、处理行分隔、新建文件、打开文件、自动保存等功能。
 */
public class Controller implements ControllerInterface {

    private static final Controller INSTANCE = new Controller();

    private Controller() {

    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    /**
     * 打开关联文件并创建文本区域。
     *
     * @param rawParameters 原始参数列表
     * @return 创建的文本区域
     */
    @Override
    public LineNumberTextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters) {
        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath);
            return null;
        } else {
            LineNumberTextArea textArea = createNewTextArea();
            configureTextArea(textArea);
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
    public EventHandler<ActionEvent> getLineFeedEventHandler(LineNumberTextArea textArea) {
        return new LineFeed(textArea);
    }

    /**
     * 获取新建文件事件处理程序。
     *
     * @param textArea 文本区域
     * @return 新建文件事件处理程序
     */
    @Override
    public EventHandler<ActionEvent> getNewFileEventHandler(LineNumberTextArea textArea) {
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
    public void autoSave(LineNumberTextArea textArea) {
        textArea.getMainTextArea().textProperty().addListener((observable, oldValue, newValue) -> {
            Tab tab = ViewManager.tabPane.getSelectionModel().getSelectedItem();
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
    public void updateStatusLabel(LineNumberTextArea textArea) {
        int caretPosition = textArea.getMainTextArea().getCaretPosition();
        int row = getRow(caretPosition, textArea.getMainTextArea().getText());
        int column = getColumn(caretPosition, textArea.getMainTextArea().getText());
        int length = textArea.getMainTextArea().getLength();
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
        LineNumberTextArea textArea = createNewTextArea();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line).append("\n");
            }
            String text = textBuilder.toString();

            Platform.runLater(() -> {
                textArea.getMainTextArea().setText(text);

                Tab tab = createNewTab(file.getName(), textArea);
                tab.setUserData(file);
                ViewManager.tabPane.getTabs().add(tab);
//                ViewManager.tabPane.sets
                ViewManager.tabPane.getSelectionModel().select(tab);
                updateStatusLabel(textArea);

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
    /**
     * 初始化标签面板。
     */
    @Override
    public void initTabPane() {
        Controller controller = new Controller();

        ViewManager.tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                // 获取新选定的标签页并关联的文本区域
                LineNumberTextArea textArea = (LineNumberTextArea) newTab.getContent();

                // 更新状态标签
                controller.updateStatusLabel(textArea);

                // 监听文本光标位置的变化，更新状态标签
                textArea.getMainTextArea().caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> controller.updateStatusLabel(textArea));

                // 更新编码标签
                controller.upDateEncodingLabel(textArea.getMainTextArea().getText());
            }
        });
    }

    @Override
    public void updateUIWithNewTextArea(LineNumberTextArea textArea) {
        Tab tab = new Tab("新建文件 " + (++ViewManager.tabIndex));
        tab.setContent(textArea);
        ViewManager.tabPane.getTabs().add(tab);
        ViewManager.tabPane.getSelectionModel().select(tab);
        updateStatusLabel(textArea);
    }

    /**
     * 配置文本区域。
     *
     * @param textArea 文本区域
     */
    private void configureTextArea(LineNumberTextArea textArea) {
        textArea.getMainTextArea().setWrapText(true);
        upDateEncodingLabel(textArea.getMainTextArea().getText());
        updateStatusLabel(textArea);

        textArea.textProperty().addListener((observable, oldValue, newValue) -> updateStatusLabel(textArea));

        autoSave(textArea);
    }

    /**
     * 创建新的文本区域。
     *
     * @return 新的文本区域
     */
    private LineNumberTextArea createNewTextArea() {
        LineNumberTextArea textArea = new LineNumberTextArea();
        textArea.setStyle(
                "-fx-border-color:white;" +
                        "-fx-background-color:white"
        );
        return textArea;
    }

    /**
     * 创建新的标签页。
     *
     * @param tabName  标签名
     * @param textArea 文本区域
     * @return 新的标签页
     */
    private Tab createNewTab(String tabName, LineNumberTextArea textArea) {
        Tab tab = new Tab(tabName);
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
        LineNumberTextArea textArea = createNewTextArea();
        return new Task<>() {
            @Override
            protected Void call() {
                getText(file);
                upDateEncodingLabel(textArea.getMainTextArea().getText());
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
