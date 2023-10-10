package org.jcnc.jnotepad.controller.event.handler.toolbar;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.app.common.constants.TextConstants;
import org.jcnc.jnotepad.app.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.app.util.FileUtil;
import org.jcnc.jnotepad.app.util.UiUtil;
import org.jcnc.jnotepad.model.entity.Cache;
import org.jcnc.jnotepad.model.entity.DirFileModel;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.ui.component.stage.dialog.factory.impl.BasicDirectoryChooserFactory;
import org.jcnc.jnotepad.ui.views.manager.DirectorySidebarManager;

import java.io.File;

/**
 * 打开文件夹處理器
 *
 * <p>当用户选择打开文件夹时，将创建一个左侧树型结构目录。</p>
 *
 * @author cccqyu
 */
public class OpenDirectory implements EventHandler<ActionEvent> {

    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();
    private static final DirectorySidebarManager DIRECTORY_SIDEBAR_MANAGER = DirectorySidebarManager.getInstance();

    public static final String GROUP = "directory";
    @Override
    public void handle(ActionEvent actionEvent) {
        // 获取缓存
        Cache cache = CACHE_MANAGER.getCache(GROUP, "openDirectory");

        // 显示文件选择对话框，并获取选中的文件
        File file = BasicDirectoryChooserFactory.getInstance().createDirectoryChooser(
                        UiResourceBundle.getContent(TextConstants.OPEN),
                        cache == null ? null : new File((String) cache.getCacheData())
                )
                .showDialog(UiUtil.getAppWindow());
        if (file == null) {
            return;
        }
        // 设置缓存
        if (cache == null) {
            CACHE_MANAGER.addCache(CACHE_MANAGER.createCache(GROUP, "openDirectory", file.getAbsolutePath(), CacheExpirationTime.NEVER_EXPIRES.getValue()));
        } else {
            cache.setCacheData(file.getParent());
            CACHE_MANAGER.addCache(cache);
        }
        flushDirSidebar(file);

    }

    public void flushDirSidebar(File file) {
        // 将文件转为实体类
        DirFileModel dirFileModel = FileUtil.getDirFileModel(file);
        // 缓存已打开的文件夹
        CACHE_MANAGER.addCache(CACHE_MANAGER.createCache(GROUP, "folderThatWasOpened", file.getAbsolutePath(), CacheExpirationTime.NEVER_EXPIRES.getValue()));
        // 打开侧边栏
        DIRECTORY_SIDEBAR_MANAGER.controlShow(true);
        // 设置文件树功能
        DIRECTORY_SIDEBAR_MANAGER.setTreeView(dirFileModel);
    }
}
