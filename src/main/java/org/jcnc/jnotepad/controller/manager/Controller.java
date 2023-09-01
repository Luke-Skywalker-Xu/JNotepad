package org.jcnc.jnotepad.controller.manager;

import org.jcnc.jnotepad.controller.event.handler.menubar.NewFile;
import org.jcnc.jnotepad.controller.event.handler.menubar.OpenFile;
import org.jcnc.jnotepad.interfaces.ControllerAble;

import java.io.File;
import java.util.List;

/**
 * 控制器类，实现 ControllerAble 接口，用于管理文本编辑器的各种操作和事件处理。
 *
 * @author 许轲
 */
public class Controller implements ControllerAble {

    private static final Controller INSTANCE = new Controller();

    private Controller() {
    }

    /**
     * 获取 Controller 的唯一实例。
     *
     * @return Controller 的实例
     */
    public static Controller getInstance() {
        return INSTANCE;
    }

    /**
     * 打开关联文件并创建文本区域。
     *
     * @param rawParameters 原始参数列表
     */
    @Override
    public void openAssociatedFileAndCreateTextArea(List<String> rawParameters) {
        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath);
        } else {
            new NewFile().addNewFileTab();
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
            new OpenFile().openFile(file);
        }
    }
}
