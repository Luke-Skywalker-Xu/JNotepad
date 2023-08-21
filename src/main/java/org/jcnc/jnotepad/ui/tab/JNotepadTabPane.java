package org.jcnc.jnotepad.ui.tab;

import javafx.scene.control.TabPane;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;

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

    /**
     * 初始化监听器
     */
    private void initListeners() {
        // tab选中行为监听器，用于tab切换后，更新与当前tab相关的组件
        this.getSelectionModel().selectedItemProperty().addListener(
                (ov, from, to) -> {
                    if (to != null) {
                        JNotepadMenuBar.getMenuBar().updateMenuStatusBySelectedTab();
                    }
                }
        );
    }

    public static JNotepadTabPane getInstance() {
        return TAB_PANE;
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
        this.getTabs().add(tab);
        this.getSelectionModel().select(tab);
    }

    /**
     * 获取选中的标签页
     *
     * @return 当前选中的标签页
     */
    public JNotepadTab getSelected() {
        return (JNotepadTab) this.getSelectionModel().getSelectedItem();
    }
}
