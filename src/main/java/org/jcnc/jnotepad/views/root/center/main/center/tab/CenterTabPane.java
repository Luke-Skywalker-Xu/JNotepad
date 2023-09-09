package org.jcnc.jnotepad.views.root.center.main.center.tab;

import javafx.scene.control.TabPane;
import org.jcnc.jnotepad.util.SingletonUtil;
import org.jcnc.jnotepad.views.root.center.main.bottom.status.BottomStatusBox;
import org.jcnc.jnotepad.views.root.top.menu.TopMenuBar;

/**
 * 标签页布局组件封装。
 *
 * @author songdragon
 */
public class CenterTabPane extends TabPane {

    private static final CenterTabPane TAB_PANE = new CenterTabPane();

    private CenterTabPane() {
        initListeners();
    }

    public static CenterTabPane getInstance() {
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
                        TopMenuBar.getInstance().updateMenuStatusBySelectedTab();
                    }
                    // 更新状态标签
                    BottomStatusBox.getInstance().updateWhenTabSelected();
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
    public CenterTab getSelected() {
        return (CenterTab) this.getSelectionModel().getSelectedItem();
    }

    /**
     * tab选中行为。
     * 应用当前菜单上选中的自动换行设置。
     */
    public void fireTabSelected() {
        CenterTab selectedTab = getSelected();
        selectedTab.setAutoLine(SingletonUtil.getAppConfigController().getAutoLineConfig());
        BottomStatusBox.getInstance().updateWhenTabSelected();
    }
}
