package org.jcnc.jnotepad.util;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.api.core.views.menu.builder.ContextMenuBuilder;
import org.jcnc.jnotepad.api.core.views.menu.builder.MenuBuilder;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.AppConstants;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.component.module.TextCodeArea;
import org.jcnc.jnotepad.component.stage.dialog.factory.impl.BasicFileChooserFactory;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.controller.event.handler.menuitem.OpenFile;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.model.entity.Cache;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.views.manager.BottomStatusBoxManager;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.manager.TopMenuBarManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;
import org.slf4j.Logger;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;

import static org.jcnc.jnotepad.controller.config.UserConfigController.CONFIG_NAME;
import static org.jcnc.jnotepad.util.FileUtil.getFileText;

/**
 * 标签页工具
 *
 * @author gewuyou
 */
public class TabUtil {
    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();
    static Logger logger = LogUtil.getLogger(TabUtil.class);

    private TabUtil() {
    }

    /**
     * 保存文件标签页
     */
    public static void saveFile(CenterTab tab) {
        if (tab == null) {
            return;
        }
        // 如果打开的是非关联文件，则调用另存为方法
        if (!tab.isRelevance()) {
            logger.info("当前保存文件为非关联打开文件，调用另存为方法");
            saveAsFile(tab);
        } else {
            logger.info("当前保存文件为关联打开文件，调用自动保存方法");
            // 调用tab保存方法
            tab.saveSelectedFileTab();
            // 如果该文件是配置文件，则刷新快捷键
            if (CONFIG_NAME.equals(tab.getText())) {
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
     * 另存为
     *
     * @apiNote 将当前选中的标签页进行另存为弹出窗口式的保存。
     * @see LogUtil
     */
    public static void saveAsFile(CenterTab tab) {
        if (tab == null) {
            return;
        }
        Cache cache = CACHE_MANAGER.getCache("folder", "saveFile");
        File file = BasicFileChooserFactory.getInstance().createFileChooser(
                        UiResourceBundle.getContent(TextConstants.SAVE_AS),
                        tab.getText(),
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
            logger.info("正在保存文件: {}", file.getName());
            tab.save(file);
            // 将保存后的文件设置为关联文件
            tab.setRelevance(true);
            // 更新标签页上的文件名
            tab.setText(file.getName());
        }
    }

    /**
     * 重命名
     */
    public static void rename(CenterTab tab) {
        if (tab == null || tab.getText().isEmpty()) {
            return;
        }
        // 判断当前是否为关联文件
        if (tab.isRelevance()) {
            // 重命名关联文件
            handleRenameRelevanceFile(tab);
        }
        // 如果当前不是关联文件则重命名标签页
        else {
            handleRenameTab(tab);
        }
    }

    /**
     * 重命名标签页。
     *
     * @param tab 标签页组件
     */
    private static void handleRenameTab(CenterTab tab) {
        TextField textField = new TextField(tab.getText());
        textField.getStyleClass().add("tab-title-editable");
        // 临时记录标签页名称
        String tempName = tab.getText();
        // 清空标签页名称
        tab.setText("");

        // 监听 Enter 键，完成编辑
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newTabName = textField.getText();
                // 检查是否存在相同名称的标签页
                if (isTabNameExists(newTabName)) {

                    // 显示弹窗并提示用户更换名称
                    showDuplicateNameAlert(newTabName);

                    // 恢复原始名称
                    tab.setText(tempName);

                } else {
                    tab.setText(newTabName);
                    // 可选：移除 TextField 的图形
                    tab.setGraphic(null);
                    // 可选：恢复标签页的关闭按钮
                    tab.setClosable(true);
                }
            }
        });

        // 监听失去焦点事件，完成编辑
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            String newTabName = textField.getText();
            // 检查是否存在相同名称的标签页
            if (isTabNameExists(newTabName)) {
                // 恢复原始名称
                tab.setText(tempName);

            }
            if (Boolean.FALSE.equals(newValue)) {
                tab.setText(newTabName);
                // 可选：移除 TextField 的图形
                tab.setGraphic(null);
                // 可选：恢复标签页的关闭按钮
                tab.setClosable(true);

            }
        });

        tab.setClosable(false);
        // 设置 TextField 作为标签页的图形
        tab.setGraphic(textField);
        // 默认获取焦点并选中所有文字
        textField.requestFocus();
        textField.selectAll();
    }

    /**
     * 判断是否存在具有相同名称的标签页。
     *
     * @param newTabName 要检查的新标签页名称
     * @return 如果存在具有相同名称的标签页，则返回 true；否则返回 false
     */
    private static boolean isTabNameExists(String newTabName) {
        CenterTabPane tabPane = CenterTabPane.getInstance();
        return tabPane.getTabs().stream()
                .anyMatch(tab -> tab.getText().equals(newTabName));
    }

    /**
     * 显示警告弹窗，提示用户更换重复的名称。
     */
    private static void showDuplicateNameAlert(String newTabName) {

        PopUpUtil.errorAlert("重命名错误", "\" " + newTabName + "\" 和已有标签页名字重复", "请再次重命名", null, null);

    }

    /**
     * 重命名关联文件。
     *
     * @param tab 标签页组件
     */
    private static void handleRenameRelevanceFile(CenterTab tab) {
        // 获取原始文件对象
        File file = (File) tab.getUserData();

        // 获取应用窗口并绑定
        File newFile = BasicFileChooserFactory.getInstance()
                .createFileChooser(
                        UiResourceBundle.getContent(TextConstants.RENAME),
                        tab.getText(),
                        new File(file.getParent()),
                        new FileChooser.ExtensionFilter("All types", "*.*"))
                .showSaveDialog(UiUtil.getAppWindow());
        if (newFile != null) {
            boolean rename = file.renameTo(newFile);
            // 设置文件数据
            tab.setUserData(newFile);
            if (rename) {
                tab.setText(newFile.getName());
                logger.info("文件重命名成功");
            } else {
                logger.debug("文件重命名失败");
            }
        }
    }

    /**
     * 添加新的文件标签页。
     */
    public static void addNewFileTab() {
        // 创建一个新的文本编辑区
        TextCodeArea textArea = new TextCodeArea();
        // 设定初始索引
        int index = 1;
        StringBuilder tabTitle = new StringBuilder();
        // 获取当前默认创建标签页集合
        List<Tab> tabs = CenterTabPane.getInstance()
                .getTabs()
                .stream()
                // 排除不属于默认创建的标签页
                .filter(tab -> AppConstants.TABNAME_PATTERN.matcher(tab.getText()).matches())
                // 对默认创建的标签页进行排序
                .sorted(Comparator.comparing(Tab::getText))
                // 转为List集合
                .toList();
        // 构建初始标签页名称
        tabTitle.append(UiResourceBundle.getContent(TextConstants.NEW_FILE)).append(index);
        for (Tab tab : tabs) {
            if (tab.getText().contentEquals(tabTitle)) {
                tabTitle.setLength(0);
                tabTitle.append(UiResourceBundle.getContent(TextConstants.NEW_FILE)).append(++index);
            } else {
                break;
            }
        }
        // 创建标签页
        CenterTab centerTab = new CenterTab(
                tabTitle.toString(),
                textArea);
        // 设置当前标签页与本地文件无关联
        centerTab.setRelevance(false);
        // 将Tab页添加到TabPane中
        CenterTabPaneManager.getInstance().addNewTab(centerTab);
        // 更新编码信息
        BottomStatusBoxManager.getInstance().updateEncodingLabel();
    }

    /**
     * 打开文件到选项卡
     *
     * @param file 文件对象
     */
    public static void openFileToTab(File file) {
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
        TextCodeArea textCodeArea = new TextCodeArea();
        // 检测文件编码
        Charset encoding = EncodingDetector.detectEncodingCharset(file);
        String fileText = getFileText(file, encoding);
        LogUtil.getLogger(OpenFile.class).info("已调用读取文件功能");
        textCodeArea.appendText(fileText);
        CenterTab tab = new CenterTab(file.getName(), textCodeArea, encoding);
        // 设置当前标签页关联本地文件
        tab.setRelevance(true);
        // 设置标签页关联文件
        tab.setUserData(file);
        // 设置关联文件最后的修改时间
        tab.setLastModifiedTimeOfAssociatedFile(file.lastModified());
        CenterTabPaneManager.getInstance().addNewTab(tab);
    }

    public static void updateTabContextMenu(CenterTab tab) {
        ContextMenuBuilder builder = new ContextMenuBuilder();
        CenterTabPaneManager centerTabPaneManager = CenterTabPaneManager.getInstance();
        File file = (File) tab.getUserData();
        //todo 设置上下文菜单
        tab.setContextMenu(
                builder
                        .addMenuItem("关闭", e -> centerTabPaneManager.removeTab(tab))
                        .addMenuItem(
                                "关闭其它标签页",
                                e -> centerTabPaneManager.removeOtherTabs(tab),
                                centerTabPaneManager.hasOtherTabs()
                        )
                        .addMenuItem("关闭所有标签页", e -> centerTabPaneManager.removeAllTabs())
                        .addMenuItem(
                                "关闭左侧标签页",
                                e -> centerTabPaneManager.removeLeftTabs(tab),
                                centerTabPaneManager.hasLeftTabs(tab)
                        )
                        .addMenuItem(
                                "关闭右侧标签页",
                                e -> centerTabPaneManager.removeRightTabs(tab),
                                centerTabPaneManager.hasRightTabs(tab)
                        )
                        .addSeparatorMenuItem()
                        .addMenu(
                                new MenuBuilder("复制")
                                        .addMenuItem("文件名", e -> {
                                            ClipboardUtil.writeTextToClipboard(tab.getText());
                                            NotificationUtil.infoNotification("已复制文件名!");
                                        }, tab.isRelevance())
                                        .addMenuItem("标签页名", e -> {
                                            ClipboardUtil.writeTextToClipboard(tab.getText());
                                            NotificationUtil.infoNotification("已复制标签页名!");
                                        }, !tab.isRelevance())
                                        .addMenuItem("文件路径", e -> {
                                            ClipboardUtil.writeTextToClipboard(file.getAbsolutePath());
                                            NotificationUtil.infoNotification("已复制文件路径!");
                                        }, tab.isRelevance())
                                        .addMenuItem("所在文件夹", e -> {
                                            ClipboardUtil.writeTextToClipboard(file.getParent());
                                            NotificationUtil.infoNotification("已复制所在文件夹!");
                                        }, tab.isRelevance())
                                        .build()
                        )
                        .addSeparatorMenuItem()
                        .addMenuItem("保存", e -> saveFile(tab))
                        .addMenuItem("另存为", e -> saveAsFile(tab), tab.isRelevance())
                        .addMenuItem("重命名", e -> rename(tab))
                        .addSeparatorMenuItem()
                        .addMenu(new MenuBuilder("打开于")
                                .addMenuItem("资源管理器", e -> FileUtil.openExplorer(file))
                                .addMenuItem("终端", e -> {
                                    //todo @luke 请你在此设置打开文件所在文件夹路径于终端
                                })
                                .build(), tab.isRelevance())
                        .addSeparatorMenuItem()
                        .addCheckMenuItem("固定标签页", e -> centerTabPaneManager.updateTabPinnedState(tab, (CheckMenuItem) e.getSource()))
                        .addSeparatorMenuItem()
                        .addCheckMenuItem("只读", e -> centerTabPaneManager.updateReadOnlyProperty(tab, (CheckMenuItem) e.getSource()))
                        .build());
    }
}
