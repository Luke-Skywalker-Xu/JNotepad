package org.jcnc.jnotepad.tool;

import javafx.stage.Window;
import org.jcnc.jnotepad.ui.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.ui.status.JNotepadStatusBox;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;
import org.jcnc.jnotepad.view.manager.ViewManager;

/**
 * UI工具<br>
 * 封装了项目所有的UI组件，以减少组件单例模式造成代码的复杂性
 *
 * @author gewuyou
 */
public class UiUtil {
    private UiUtil() {
    }

    /**
     * 标签页布局组件
     */
    private static final JNotepadTabPane TAB_PANE = JNotepadTabPane.getInstance();
    /**
     * 视图管理组件
     */
    private static final ViewManager VIEW_MANAGER = ViewManager.getInstance();
    /**
     * 状态栏组件
     */
    private static final JNotepadStatusBox STATUS_BOX = JNotepadStatusBox.getInstance();
    /**
     * 菜单栏组件
     */
    private static final JNotepadMenuBar MENU_BAR = JNotepadMenuBar.getMenuBar();

    /**
     * 获取标签页布局组件
     *
     * @return org.jcnc.jnotepad.ui.tab.JNotepadTabPane
     * @apiNote JNotepadTabPane.getInstance()
     * @see JNotepadTabPane
     */

    public static JNotepadTabPane getJnotepadTabPane() {
        return TAB_PANE;
    }

    /**
     * 获取标签页组件
     *
     * @return org.jcnc.jnotepad.ui.tab.JNotepadTab
     * @apiNote JNotepadTabPane.getInstance().getSelected()<br>获取当前选中的标签页
     * @see JNotepadTabPane
     */

    public static JNotepadTab getJnotepadtab() {
        return TAB_PANE.getSelected();
    }

    /**
     * 获取应用窗口
     *
     * @return javafx.stage.Window
     * @apiNote JNotepadTabPane.getInstance().getSelected().getTabPane().getScene().getWindow()
     * @since 2023/8/29 14:12
     */
    public static Window getAppWindow() {
        return TAB_PANE.getSelected().getTabPane().getScene().getWindow();
    }

    /**
     * 获取视图管理组件
     *
     * @return org.jcnc.jnotepad.view.manager.ViewManager
     * @apiNote ViewManager.getInstance()
     * @since 2023/8/29 14:13
     */
    public static ViewManager getViewManager() {
        return VIEW_MANAGER;
    }

    /**
     * 获取状态栏组件
     *
     * @return org.jcnc.jnotepad.ui.status.JNotepadStatusBox
     * @since 2023/8/29 14:14
     */
    public static JNotepadStatusBox getStatusBox() {
        return STATUS_BOX;
    }

    /**
     * 获取菜单栏组件
     *
     * @return org.jcnc.jnotepad.ui.menu.JNotepadMenuBar
     * @since 2023/8/29 14:15
     */
    public static JNotepadMenuBar getMenuBar() {
        return MENU_BAR;
    }
}
