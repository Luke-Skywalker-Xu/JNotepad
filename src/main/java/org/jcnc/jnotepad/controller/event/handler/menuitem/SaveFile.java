package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.component.stage.dialog.factory.impl.BasicFileChooserFactory;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.model.entity.Cache;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.manager.TopMenuBarManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.slf4j.Logger;

import java.io.File;

import static org.jcnc.jnotepad.controller.config.UserConfigController.CONFIG_NAME;

/**
 * 保存文件事件处理程序。
 * <p>
 * 当用户选择保存文件时，如果当前标签页是关联文件，则自动保存；
 * 否则，调用另存为方法。
 *
 * @author gewuyou
 */
public class SaveFile implements EventHandler<ActionEvent> {
    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 处理保存文件事件。
     *
     * @param actionEvent 事件对象
     * @apiNote 当用户选择保存文件时，如果当前标签页是关联文件，则自动保存；
     * 否则，调用另存为方法。
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // 获取当前tab页
        CenterTab selectedTab = CenterTabPaneManager.getInstance().getSelected();
        if (selectedTab == null) {
            return;
        }
        // 如果打开的是非关联文件，则调用另存为方法
        if (!selectedTab.isRelevance()) {
            logger.info("当前保存文件为非关联打开文件，调用另存为方法");
            saveTab(this.getClass());
        } else {
            logger.info("当前保存文件为关联打开文件，调用自动保存方法");
            // 调用tab保存方法
            selectedTab.saveSelectedFileTab();
            // 如果该文件是配置文件，则刷新快捷键
            if (CONFIG_NAME.equals(selectedTab.getText())) {
                // 重新加载语言包和快捷键
                UserConfigController.getInstance().loadConfig();
                TopMenuBarManager.getInstance().initShortcutKeys();
                LocalizationController.initLocal();
                logger.info("已刷新语言包！");
                logger.info("已刷新快捷键！");
            }
        }
    }

    /**
     * 保存标签页的方法。
     *
     * @param currentClass 调用该方法的类
     * @apiNote 将当前选中的标签页进行另存为弹出窗口式的保存。
     * @see LogUtil
     */
    protected void saveTab(Class<?> currentClass) {
        CenterTab selectedTab = CenterTabPaneManager.getInstance().getSelected();
        if (selectedTab == null) {
            return;
        }
        Cache cache = CACHE_MANAGER.getCache("folder", "saveFile");
        File file = BasicFileChooserFactory.getInstance().createFileChooser(
                        UiResourceBundle.getContent(TextConstants.SAVE_AS),
                        selectedTab.getText(),
                        cache == null ? null : new File((String) cache.getCacheData()),
                        new FileChooser.ExtensionFilter("All types", "*.*"))
                .showSaveDialog(UiUtil.getAppWindow());
        if (file != null) {
            if (cache == null) {
                CACHE_MANAGER.addCache(CACHE_MANAGER.createCache("folder", "saveFile", file.getParent(), CacheExpirationTime.NEVER_EXPIRES.getValue()));
            } else {
                cache.setCacheData(file.getParent());
                CACHE_MANAGER.addCache(cache);
            }
            LogUtil.getLogger(currentClass).info("正在保存文件: {}", file.getName());
            selectedTab.save(file);
            // 将保存后的文件设置为关联文件
            selectedTab.setRelevance(true);
            // 更新标签页上的文件名
            selectedTab.setText(file.getName());
        }
    }
}
