package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.component.module.TextCodeArea;
import org.jcnc.jnotepad.component.stage.dialog.factory.impl.BasicFileChooserFactory;
import org.jcnc.jnotepad.model.entity.Cache;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.util.EncodingDetector;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;

import java.io.File;
import java.nio.charset.Charset;

import static org.jcnc.jnotepad.util.FileUtil.getFileText;


/**
 * 打开文件的事件处理程序。
 * <p>
 * 当用户选择打开文件时，将创建一个新的文本编辑区，并在Tab页中显示。
 *
 * @author 许轲
 */
public class OpenFile implements EventHandler<ActionEvent> {
    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();

    /**
     * 处理打开文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        // 获取缓存
        Cache cache = CACHE_MANAGER.getCache("folder", "openFile");
        // 显示文件选择对话框，并获取选中的文件
        File file = BasicFileChooserFactory.getInstance().createFileChooser(
                        UiResourceBundle.getContent(TextConstants.OPEN),
                        null,
                        cache == null ? null : new File((String) cache.getCacheData()),
                        new FileChooser.ExtensionFilter("All types", "*.*"))
                .showOpenDialog(UiUtil.getAppWindow());
        if (file == null) {
            return;
        }
        // 设置缓存
        if (cache == null) {
            CACHE_MANAGER.addCache(CACHE_MANAGER.createCache("folder", "openFile", file.getParent(), CacheExpirationTime.NEVER_EXPIRES.getValue()));
        } else {
            cache.setCacheData(file.getParent());
            CACHE_MANAGER.addCache(cache);
        }
        openFile(file);
    }

    /**
     * 打开文件。
     *
     * @param file 文件对象
     */
    public static void openFile(File file) {
        // 获取标签页集合
        CenterTabPane centerTabPane = CenterTabPane.getInstance();
        // 遍历标签页，查找匹配的标签页
        for (Tab tab : centerTabPane.getTabs()) {
            // 获取绑定的文件
            File tabFile = (File) tab.getUserData();
            if (tabFile == null) {
                continue;
            }
            if (file.getPath().equals((tabFile).getPath())) {
                // 找到匹配的标签页，设置为选中状态并跳转
                centerTabPane.getSelectionModel().select(tab);
                return;
            }
        }
        getText(file);
    }

    /**
     * 读取文本文件的内容。
     *
     * @param file 文件对象
     */
    public static void getText(File file) {
        TextCodeArea textCodeArea = createNewTextArea();
        // 检测文件编码
        Charset encoding = EncodingDetector.detectEncodingCharset(file);
        String fileText = getFileText(file, encoding);
        LogUtil.getLogger(OpenFile.class).info("已调用读取文件功能");
        textCodeArea.appendText(fileText);
        CenterTab tab = createNewTab(file.getName(), textCodeArea, encoding);
        // 设置当前标签页关联本地文件
        tab.setRelevance(true);
        // 设置标签页关联文件
        tab.setUserData(file);
        // 设置关联文件最后的修改时间
        tab.setLastModifiedTimeOfAssociatedFile(file.lastModified());
        CenterTabPaneManager.getInstance().addNewTab(tab);
    }


    /**
     * 创建新的文本区域。
     *
     * @return 新的文本区域
     */
    private static TextCodeArea createNewTextArea() {
        return new TextCodeArea();
    }

    /**
     * 创建新的标签页。
     *
     * @param tabName  标签名
     * @param textCodeArea 文本区域
     * @return 新的标签页
     */
    private static CenterTab createNewTab(String tabName, TextCodeArea textCodeArea, Charset charset) {
        return new CenterTab(tabName, textCodeArea, charset);
    }
}
