package org.jcnc.jnotepad.Interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.List;

/**
 * 控制器接口类
 *
 */
public interface ControllerInterface {

    /**
     * 打开关联文件并创建 TextArea
     *
     * @param rawParameters 原始参数列表
     * @return 创建的 TextArea
     */
    TextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters);

    /**
     * 获取换行符处理事件处理程序
     *
     * @param textArea 文本区域
     * @return 换行符处理事件处理程序
     */
    EventHandler<ActionEvent> getLineFeedEventHandler(TextArea textArea);

    /**
     * 获取新建文件处理事件处理程序
     *
     * @param textArea 文本区域
     * @return 新建文件处理事件处理程序
     */
    EventHandler<ActionEvent> getNewFileEventHandler(TextArea textArea);

    /**
     * 获取打开文件处理事件处理程序
     *
     * @return 打开文件处理事件处理程序
     */
    EventHandler<ActionEvent> getOpenFileEventHandler();

    /**
     * 获取保存文件处理事件处理程序
     *
     * @return 保存文件处理事件处理程序
     */
    EventHandler<ActionEvent> getSaveFileEventHandler();

    /**
     * 获取另存为文件处理事件处理程序
     *
     * @return 另存为文件处理事件处理程序
     */
    EventHandler<ActionEvent> getSaveAsFileEventHandler();


    /**
     * 自动保存
     *
     * @param textArea 文本区域
     */
    void autoSave(TextArea textArea);

    /**
     * 更新状态标签
     *
     * @param textArea 文本区域
     */
    void updateStatusLabel(TextArea textArea);

    /**
     * 打开关联文件
     *
     * @param filePath 文件路径
     */
    void openAssociatedFile(String filePath);

    /**
     * 获取文件内容
     *
     * @param file 文件
     */
    void getText(File file);

    /**
     * 更新编码标签
     *
     * @param text 编码标签文本
     */
    void upDateEncodingLabel(String text);

    /**
     * 获取光标所在行号
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     * @return 行号
     */
    int getRow(int caretPosition, String text);

    /**
     * 获取光标所在列号
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     * @return 列号
     */
    int getColumn(int caretPosition, String text);

    /**
     * 初始化 TabPane
     */
    void initTabPane();

    /**
     * 另存为文件
     */
    void saveAsFile();

}
