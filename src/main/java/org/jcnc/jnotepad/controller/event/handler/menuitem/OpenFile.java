package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.component.stage.dialog.factory.impl.BasicFileChooserFactory;
import org.jcnc.jnotepad.model.entity.Cache;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.util.UiUtil;

import java.io.File;

import static org.jcnc.jnotepad.util.TabUtil.openFileToTab;


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
        openFileToTab(file);
    }
}
