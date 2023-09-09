package org.jcnc.jnotepad.common.interfaces;

import java.util.List;

/**
 * 控制器接口类
 *
 * <p>该接口定义了控制器的方法，用于打开关联文件并创建TextArea。</p>
 *
 * @author 许轲
 */
public interface ControllerAble {

    /**
     * 打开关联文件并创建 TextArea。
     *
     * @param rawParameters 原始参数列表
     */
    void openAssociatedFileAndCreateTextArea(List<String> rawParameters);

    /**
     * 打开关联文件。
     *
     * @param filePath 文件路径
     */
    void openAssociatedFile(String filePath);
}
