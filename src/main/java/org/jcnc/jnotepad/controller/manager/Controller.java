package org.jcnc.jnotepad.controller.manager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.jcnc.jnotepad.Interface.ControllerInterface;
import org.jcnc.jnotepad.app.i18n.UIResourceBundle;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.manager.ThreadPoolManager;
import org.jcnc.jnotepad.tool.EncodingDetector;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.jcnc.jnotepad.manager.ThreadPoolManager.threadContSelfSubtracting;

/**
 * 控制器类，实现ControllerInterface接口，用于管理文本编辑器的各种操作和事件处理。
 * 包括打开关联文件、创建文本区域、处理行分隔、新建文件、打开文件、自动保存等功能。
 *
 * @author 许轲
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
            // 设置当前标签页与本地文件无关联
            textArea.setRelevance(false);
            configureTextArea(textArea);
            return textArea;
        }
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
        // 设置当前标签页关联本地文件
        textArea.setRelevance(true);
        // 检测文件编码
        Charset encoding = EncodingDetector.detectEncodingCharset(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file, encoding))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line).append("\n");
            }
            String text = textBuilder.toString();
            LogUtil.getLogger(this.getClass()).info("已调用读取文件功能");
            Platform.runLater(() -> {
                textArea.getMainTextArea().setText(text);
                JNotepadTab tab = createNewTab(file.getName(), textArea, encoding);
                tab.setUserData(file);
                JNotepadTabPane.getInstance().addNewTab(tab);
            });

        } catch (IOException ignored) {
            LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
    }

    /**
     * 更新UI和标签页
     *
     * @param textArea 文本域
     * @apiNote
     * @since 2023/8/20 12:40
     */
    @Override
    public void updateUiWithNewTextArea(LineNumberTextArea textArea) {
        ViewManager viewManager = ViewManager.getInstance();
        String tabTitle = UIResourceBundle.getContent(TextConstants.NEW_FILE) + viewManager.selfIncreaseAndGetTabIndex();
        JNotepadTabPane.getInstance().addNewTab(new JNotepadTab(tabTitle, textArea));
    }


    /**
     * 配置文本区域。
     *
     * @param textArea 文本区域
     */
    private void configureTextArea(LineNumberTextArea textArea) {
        textArea.getMainTextArea().setWrapText(true);
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
    private JNotepadTab createNewTab(String tabName, LineNumberTextArea textArea, Charset charset) {
        return new JNotepadTab(tabName, textArea, charset);
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
    private void openFile(File file) {
        ThreadPoolManager.getThreadPool().submit(createOpenFileTask(file));
    }

}
