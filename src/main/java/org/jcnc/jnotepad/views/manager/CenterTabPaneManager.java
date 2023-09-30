package org.jcnc.jnotepad.views.manager;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;
import org.jcnc.jnotepad.views.root.top.menu.TopMenuBar;

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
                (ov, from, to) -> {
                    if (to != null) {
                        // 更新菜单栏中与tab相关设置
                        TopMenuBar.getInstance().updateMenuStatusBySelectedTab();
                    }
                    // 更新状态标签
                    bottomStatusBoxManager.updateWhenTabSelected();
                }
        );
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
        selectedTab.setAutoLine(AppConfigController.getInstance().getAutoLineConfig());
        bottomStatusBoxManager.updateWhenTabSelected();
    }

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
}
