package org.jcnc.jnotepad.controller.manager;

import org.jcnc.jnotepad.common.interfaces.ControllerAble;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.controller.event.handler.menuitem.NewFile;
import org.jcnc.jnotepad.controller.event.handler.menuitem.OpenFile;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 控制器类，实现 ControllerAble 接口，用于管理文本编辑器的各种操作和事件处理。
 *
 * @author 许轲
 */
public class Controller implements ControllerAble {
    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();

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
        // 获取上次打开的页面
        Optional<Object> cacheData = Optional.ofNullable(CACHE_MANAGER.getCacheData("tabs", "centerTabs"));
        // 判空
        List<String> fileTab = (List<String>) cacheData.orElse(Collections.emptyList());
        // 打开上次打开的标签页
        fileTab.forEach(filePath -> OpenFile.openFile(new File(filePath)));

        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath);
            return;
        }
        if (fileTab.isEmpty()) {
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
            OpenFile.openFile(file);
        }
    }
}
