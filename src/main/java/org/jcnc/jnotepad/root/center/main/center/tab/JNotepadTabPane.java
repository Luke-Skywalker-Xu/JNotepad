package org.jcnc.jnotepad.root.center.main.center.tab;

import javafx.scene.control.TabPane;
import org.jcnc.jnotepad.root.center.main.bottom.status.JNotepadStatusBox;
import org.jcnc.jnotepad.root.top.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.tool.SingletonUtil;

/**
 * 标签页布局组件封装。
 *
 * @author songdragon
 */
public class JNotepadTabPane extends TabPane {

    private static final JNotepadTabPane TAB_PANE = new JNotepadTabPane();

    private JNotepadTabPane() {
        initListeners();
    }

    public static JNotepadTabPane getInstance() {
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
                        JNotepadMenuBar.getInstance().updateMenuStatusBySelectedTab();
                    }
                    // 更新状态标签
                    JNotepadStatusBox.getInstance().updateWhenTabSelected();
                }
        );
    }

    /**
     * 添加新tab并设置为选中状态
     *
     * @param tab 新标签页
     */
    public void addNewTab(JNotepadTab tab) {
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
    public JNotepadTab getSelected() {
        return (JNotepadTab) this.getSelectionModel().getSelectedItem();
    }

    /**
     * tab选中行为。
     * 应用当前菜单上选中的自动换行设置。
     */
    public void fireTabSelected() {
        JNotepadTab selectedTab = getSelected();
        selectedTab.setAutoLine(SingletonUtil.getAppConfigController().getAutoLineConfig());
        JNotepadStatusBox.getInstance().updateWhenTabSelected();
    }
}
