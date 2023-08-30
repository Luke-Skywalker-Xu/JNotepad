package org.jcnc.jnotepad.controller.event.handler.menuBar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.UiUtil;
import org.jcnc.jnotepad.ui.root.center.tab.JNotepadTab;
import org.slf4j.Logger;

import java.io.File;

import static org.jcnc.jnotepad.controller.config.AppConfigController.CONFIG_NAME;

/**
 * 保存文件
 *
 * @author gewuyou
 */
public class SaveFile implements EventHandler<ActionEvent> {
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 保存文件
     *
     * @param actionEvent 事件对象
     * @apiNote
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // 获取当前tab页
        JNotepadTab selectedTab = UiUtil.getJnotepadtab();
        if (selectedTab == null) {
            return;
        }
        // 打开的是非关联文件，则调用另存为api
        if (!selectedTab.isRelevance()) {
            logger.info("当前保存文件为非关联打开文件，调用另存为方法");
            saveTab(this.getClass());
        } else {
            logger.info("当前保存文件为关联打开文件，调用自动保存方法");
            // 调用tab保存
            selectedTab.save();
            // 如果该文件是配置文件则刷新快捷键
            if (CONFIG_NAME.equals(selectedTab.getText())) {
                // 重新加载语言包和快捷键
                AppConfigController.getInstance().loadConfig();
                UiUtil.getMenuBar().initShortcutKeys();
                LocalizationController.initLocal();
                logger.info("已刷新语言包!");
                logger.info("已刷新快捷键!");
            }
        }
    }

    /**
     * 保存页面方法
     *
     * @param currentClass 调用该方法的类
     * @apiNote 将当前选中的标签页进行弹出窗口式的保存
     * @see LogUtil
     */
    protected void saveTab(Class<?> currentClass) {
        JNotepadTab selectedTab = UiUtil.getJnotepadtab();
        if (selectedTab != null) {
            // 创建一个文件窗口
            FileChooser fileChooser = new FileChooser();
            // 设置保存文件名称
            fileChooser.setInitialFileName(selectedTab.getText());
            // 设置保存文件类型
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
            // 获取应用窗口并绑定
            File file = fileChooser.showSaveDialog(UiUtil.getAppWindow());
            if (file != null) {
                LogUtil.getLogger(currentClass).info("正在保存文件:{}", file.getName());
                selectedTab.save();
                // 将保存后的文件设置为已关联
                selectedTab.setRelevance(true);
                // 更新Tab页标签上的文件名
                selectedTab.setText(file.getName());
                // 将文件对象保存到Tab页的UserData中
                selectedTab.setUserData(file);
            }
        }
    }
}
