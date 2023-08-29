package org.jcnc.jnotepad.Interface;

import java.util.List;

/**
 * 控制器接口类
 *
 * @author 许轲
 */
public interface ControllerInterface {

    /**
     * 打开关联文件并创建 TextArea
     *
     * @param rawParameters 原始参数列表
     * @return 创建的 TextArea
     */
    void openAssociatedFileAndCreateTextArea(List<String> rawParameters);

    /**
     * 打开关联文件
     *
     * @param filePath 文件路径
     */
    void openAssociatedFile(String filePath);

}
