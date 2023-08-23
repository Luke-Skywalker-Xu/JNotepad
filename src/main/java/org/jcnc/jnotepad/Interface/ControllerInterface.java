package org.jcnc.jnotepad.Interface;

import org.jcnc.jnotepad.ui.LineNumberTextArea;

import java.io.File;
import java.util.List;

/**
 * 控制器接口类
 * @author 许轲
 */
public interface ControllerInterface {

    /**
     * 打开关联文件并创建 TextArea
     *
     * @param rawParameters 原始参数列表
     * @return 创建的 TextArea
     */
    LineNumberTextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters);

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
    LineNumberTextArea getText(File file);

    /**
     * 更新UI和标签页
     *
     * @param textArea   文本域
     * @apiNote
     * @since 2023/8/20 12:40
     */

    void updateUiWithNewTextArea(LineNumberTextArea textArea);
}
