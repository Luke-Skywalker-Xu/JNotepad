package org.jcnc.jnotepad.root.center.main.center.tab;

import javafx.scene.control.TabPane;
import org.jcnc.jnotepad.tool.SingletonUtil;
import org.jcnc.jnotepad.tool.UiUtil;

/**
 * 标签页布局组件封装。
 *
 * @author songdragon
 */
public class MainTabPane extends TabPane {

    private static final MainTabPane TAB_PANE = new MainTabPane();

    private MainTabPane() {
        initListeners();
    }

    public static MainTabPane getInstance() {
        return TAB_PANE;
    }

    /**
     * 初始化监听器
     */
    private void initListeners() {
        // tab选中行为监听器，用于tab切换后，更新与当前tab相关的组件
        this.getSelectionModel().selectedItemProperty().addListener(
                (ov, from, to) -> {
                    if (to != null) {
                        // 更新菜单栏中与tab相关设置
                        UiUtil.getMenuBar().updateMenuStatusBySelectedTab();
                    }
                    // 更新状态标签
                    UiUtil.getStatusBox().updateWhenTabSelected();
                }
        );
    }

    /**
     * 添加新tab并设置为选中状态
     *
     * @param tab 新标签页
     */
    public void addNewTab(MainTab tab) {
        if (tab == null) {
            return;
        }
        // 将标签页加入标签页列表
        this.getTabs().add(tab);
        // 设置索引
        this.getSelectionModel().select(tab);
        // 将标签页设置为选中状态
        fireTabSelected();
    }

    /**
     * 获取选中的标签页
     *
     * @return 当前选中的标签页
     */
    public MainTab getSelected() {
        return (MainTab) this.getSelectionModel().getSelectedItem();
    }

    /**
     * tab选中行为。
     * 应用当前菜单上选中的自动换行设置。
     */
    public void fireTabSelected() {
        MainTab selectedTab = getSelected();
        selectedTab.setAutoLine(SingletonUtil.getAppConfigController().getAutoLineConfig());
        UiUtil.getStatusBox().updateWhenTabSelected();
    }
}
