package org.jcnc.jnotepad.controller.event.handler.menubar;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.manager.ThreadPoolManager;
import org.jcnc.jnotepad.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.root.center.main.center.tab.CenterTabPane;
import org.jcnc.jnotepad.tool.EncodingDetector;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.UiUtil;
import org.jcnc.jnotepad.ui.dialog.factory.impl.BasicFileChooserFactory;
import org.jcnc.jnotepad.ui.module.LineNumberTextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.jcnc.jnotepad.manager.ThreadPoolManager.threadContSelfSubtracting;

/**
 * 打开文件的事件处理程序。
 * <p>
 * 当用户选择打开文件时，将创建一个新的文本编辑区，并在Tab页中显示。
 *
 * @author 许轲
 */
public class OpenFile implements EventHandler<ActionEvent> {
    /**
     * 处理打开文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        // 显示文件选择对话框，并获取选中的文件
        File file = BasicFileChooserFactory.getInstance().createFileChooser(
                        UiResourceBundle.getContent(TextConstants.OPEN),
                        null,
                        null,
                        new FileChooser.ExtensionFilter("All types", "*.*"))
                .showOpenDialog(UiUtil.getAppWindow());
        if (file == null) {
            return;
        }
        openFile(file);
    }

    /**
     * 创建打开文件的任务。
     *
     * @param file 文件对象
     * @return 打开文件的任务
     */
    public Task<Void> createOpenFileTask(File file) {
        Task<Void> openFileTask = new Task<>() {
            @Override
            protected Void call() {
                getText(file);
                return null;
            }

        };
        // 设置任务成功完成时的处理逻辑
        openFileTask.setOnSucceeded(e -> threadContSelfSubtracting());

        // 设置任务失败时的处理逻辑
        openFileTask.setOnFailed(e -> threadContSelfSubtracting());
        return openFileTask;
    }

    /**
     * 打开文件。
     *
     * @param file 文件对象
     */
    public void openFile(File file) {
        // 获取标签页集合
        CenterTabPane jnotepadTabPane = CenterTabPane.getInstance();
        // 遍历标签页，查找匹配的标签页
        for (Tab tab : jnotepadTabPane.getTabs()) {
            // 获取绑定的文件
            File tabFile = (File) tab.getUserData();
            if (tabFile == null) {
                continue;
            }
            if (file.getPath().equals((tabFile).getPath())) {
                // 找到匹配的标签页，设置为选中状态并跳转
                jnotepadTabPane.getSelectionModel().select(tab);
                return;
            }
        }
        ThreadPoolManager.getThreadPool().submit(createOpenFileTask(file));
    }

    /**
     * 读取文本文件的内容。
     *
     * @param file 文件对象
     */
    public void getText(File file) {
        LineNumberTextArea textArea = createNewTextArea();
        // 检测文件编码
        Charset encoding = EncodingDetector.detectEncodingCharset(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file, encoding))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!textBuilder.isEmpty()) {
                    textBuilder.append("\n");
                }
                textBuilder.append(line);
            }
            String text = textBuilder.toString();
            LogUtil.getLogger(this.getClass()).info("已调用读取文件功能");
            Platform.runLater(() -> {
                textArea.getMainTextArea().setText(text);
                CenterTab tab = createNewTab(file.getName(), textArea, encoding);
                // 设置当前标签页关联本地文件
                tab.setRelevance(true);
                tab.setUserData(file);
                CenterTabPane.getInstance().addNewTab(tab);
            });
        } catch (IOException ignored) {
            LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
    }

    /**
     * 创建新的文本区域。
     *
     * @return 新的文本区域
     */
    private LineNumberTextArea createNewTextArea() {
        return new LineNumberTextArea();
    }

    /**
     * 创建新的标签页。
     *
     * @param tabName  标签名
     * @param textArea 文本区域
     * @return 新的标签页
     */
    private CenterTab createNewTab(String tabName, LineNumberTextArea textArea, Charset charset) {
        return new CenterTab(tabName, textArea, charset);
    }
}
