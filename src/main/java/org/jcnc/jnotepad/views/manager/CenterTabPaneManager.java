package org.jcnc.jnotepad.views.manager;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.component.module.TextCodeArea;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.util.FileUtil;
import org.jcnc.jnotepad.util.PopUpUtil;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;
import org.jcnc.jnotepad.views.root.top.menubar.TopMenuBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 中心标签页窗格管理类
 *
 * @author gewuyou
 */
public class CenterTabPaneManager {
    private static final ApplicationCacheManager CACHE_MANAGER = ApplicationCacheManager.getInstance();
    private static final CenterTabPaneManager INSTANCE = new CenterTabPaneManager();

    private final CenterTabPane centerTabPane = CenterTabPane.getInstance();

    private final BottomStatusBoxManager bottomStatusBoxManager = BottomStatusBoxManager.getInstance();

    private CenterTabPaneManager() {

    }

    public static CenterTabPaneManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化标签页布局组件
     */
    public void initCenterTabPane() {
        initListeners();
    }


    /**
     * 初始化监听器
     */
    private void initListeners() {
        // tab选中行为监听器，用于tab切换后，更新与当前tab相关的组件
        centerTabPane.getSelectionModel().selectedItemProperty().addListener(
                (ov, preTab, currTab) -> {
                    if (currTab != null) {
                        // 更新菜单栏中与tab相关设置
                        TopMenuBar.getInstance().updateMenuStatusBySelectedTab();
                        // 判断当前标签页是否关联文件
                        CenterTab tab = (CenterTab) currTab;
                        // 检查文件标签页状态
                        checkFileTabStatus(tab);
                    }
                    // 更新状态标签
                    bottomStatusBoxManager.updateWhenTabSelected();
                }
        );
    }


    /**
     * 检查文件标签页状态
     *
     * @apiNote 该方法检查当前文件是否被修改，如果被修改，则返回true
     */
    public void checkFileTabStatus(CenterTab tab) {
        if (tab == null) {
            return;
        }
        if (tab.isRelevance()) {
            // 获取当前文本域对象
            TextCodeArea textCodeArea = tab.getTextCodeArea();
            // 获取当前标签页对应文件上次修改时间
            Long lastModifiedTime = tab.getLastModifiedTimeOfAssociatedFile();
            // 获取对应文件上次修改时间
            File file = (File) tab.getUserData();

            Long lastModifiedTimeOfFile = file.lastModified();
            if (lastModifiedTimeOfFile.equals(lastModifiedTime)) {
                return;
            }
            //fixme 这行代码不能直接放到绑定的方法中,猜测匿名内部类的延迟执行特性可能会导致在获取 FileUtil.getFileText(file) 的返回值时，文件内容还没有被正确读取，导致空串，暂无解决办法
            String fileText = FileUtil.getFileText(file);
            // 当前文件已被外部修改
            PopUpUtil.questionAlert(
                    "重新加载", file.getAbsolutePath(), "此文件已被外部修改，是否重新加载该文件？",
                    appDialog -> {
                        textCodeArea.clear();
                        textCodeArea.appendText(fileText);
                        appDialog.close();
                    }, Stage::close, "是", "否");
        }
    }

    /**
     * 添加新tab并设置为选中状态
     *
     * @param tab 新标签页
     */
    public void addNewTab(CenterTab tab) {
        if (tab == null) {
            return;
        }
        // 将标签页加入标签页列表
        centerTabPane.getTabs().add(tab);
        // 设置索引
        centerTabPane.getSelectionModel().select(tab);
        // 将标签页设置为选中状态
        fireTabSelected();
    }

    /**
     * 获取选中的标签页
     *
     * @return 当前选中的标签页
     */
    public CenterTab getSelected() {
        return (CenterTab) centerTabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * tab选中行为。
     * 应用当前菜单上选中的自动换行设置。
     */
    public void fireTabSelected() {
        CenterTab selectedTab = getSelected();
        if (selectedTab == null) {
            return;
        }
        selectedTab.setAutoLine(UserConfigController.getInstance().getAutoLineConfig());
        bottomStatusBoxManager.updateWhenTabSelected();
    }

    /**
     * 保存当前所有打开的文件标签页
     */
    public void saveOpenFileTabs() {
        // 获取当前所有标签页
        ObservableList<Tab> tabs = centerTabPane.getTabs();
        List<String> filePaths = new ArrayList<>();
        // 缓存当前打开关联的文件
        tabs.forEach(tab -> {
            File file = (File) tab.getUserData();
            if (file != null) {
                filePaths.add(file.getPath());
            }
        });
        CACHE_MANAGER.addCache(CACHE_MANAGER.createCache("tabs", "centerTabs", filePaths, CacheExpirationTime.NEVER_EXPIRES.getValue()));
    }

    /**
     * Removes the specified CenterTab from the Tabs.
     *
     * @param tab the CenterTab to be removed
     */
    public void removeTab(CenterTab tab) {
        centerTabPane.getTabs().remove(tab);
    }

    /**
     * Removes all tabs from the center tab pane.
     */
    public void removeAllTabs() {
        centerTabPane.getTabs().clear();
    }

    /**
     * Removes all tabs from the center tab pane that are not equal to the specified center tab.
     *
     * @param centerTab the center tab to compare against
     */
    public void removeOtherTabs(CenterTab centerTab) {
        centerTabPane.getTabs().removeIf(tab -> !centerTab.equals(tab));
    }

    /**
     * This function removes the left tabs from the given CenterTab object.
     *
     * @param centerTab the CenterTab object from which to remove the left tabs
     */
    public void removeLeftTabs(CenterTab centerTab) {
        ObservableList<Tab> tabs = centerTabPane.getTabs();
        tabs.remove(0, tabs.indexOf(centerTab));
    }

    /**
     * Removes all tabs to the right of the specified center tab.
     *
     * @param centerTab the center tab to remove right tabs from
     */
    public void removeRightTabs(CenterTab centerTab) {
        ObservableList<Tab> tabs = centerTabPane.getTabs();
        tabs.remove(tabs.indexOf(centerTab), tabs.size());
    }

    /**
     * 判断是否有其它标签页
     *
     * @return 是否有其它标签页
     */
    public boolean hasOtherTabs() {
        return centerTabPane.getTabs().size() > 1;
    }


    /**
     * 判断是否有左侧标签页
     *
     * @param centerTab 标签页
     * @return 是否有左侧标签页
     */
    public boolean hasLeftTabs(CenterTab centerTab) {
        int index = centerTabPane.getTabs().indexOf(centerTab);
        return index > 0;
    }

    /**
     * 判断是否有右侧标签页
     *
     * @param centerTab 标签页
     * @return 是否有右侧标签页
     */
    public boolean hasRightTabs(CenterTab centerTab) {
        ObservableList<Tab> tabs = centerTabPane.getTabs();
        int index = tabs.indexOf(centerTab);
        return index != tabs.size() - 1;
    }
}
