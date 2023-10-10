package org.jcnc.jnotepad.component.module.interfaces;

/**
 * 控制器接口类。
 *
 * <p>该接口定义了控制器的方法，用于打开关联文件并创建 TextArea。</p>
 *
 * <p>实现此接口的类可以通过调用方法来打开关联文件并创建 TextArea，以执行与文件操作相关的控制逻辑。</p>
 *
 * @param <T> 原始参数的类型
 * @author luke
 */
public interface ControllerAble<T> {

    /**
     * 打开关联文件并创建 TextArea。
     *
     * @param rawParameters 原始参数列表
     */
    void openAssociatedFileAndCreateTextArea(T rawParameters);

    /**
     * 打开关联文件。
     *
     * @param filePath 文件路径
     */
    void openAssociatedFile(String filePath);
}
