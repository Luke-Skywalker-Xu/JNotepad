package org.jcnc.jnotepad.tool;

import javafx.scene.image.Image;
import javafx.stage.Window;
import org.jcnc.jnotepad.constants.AppConstants;
import org.jcnc.jnotepad.root.center.main.bottom.status.StatusHorizontalBox;
import org.jcnc.jnotepad.root.center.main.center.tab.MainTab;
import org.jcnc.jnotepad.root.center.main.center.tab.MainTabPane;
import org.jcnc.jnotepad.root.top.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.ui.setstage.SetStage;
import org.jcnc.jnotepad.view.manager.ViewManager;

import java.util.Objects;

/**
 * UI工具
 *
 * <p>封装了项目中所有的UI组件，以减少组件单例模式造成的代码复杂性。</p>
 *
 * @author gewuyou
 */
public class UiUtil {
    /**
     * 标签页布局组件
     */
    private static final MainTabPane TAB_PANE = MainTabPane.getInstance();
    /**
     * 视图管理组件
     */
    private static final ViewManager VIEW_MANAGER = ViewManager.getInstance();
    /**
     * 状态栏组件
     */
    private static final StatusHorizontalBox STATUS_BOX = StatusHorizontalBox.getInstance();
    /**
     * 菜单栏组件
     */
    private static final JNotepadMenuBar MENU_BAR = JNotepadMenuBar.getInstance();
    /**
     * 应用程序图标
     */
    private static final Image ICON = new Image(Objects.requireNonNull(UiUtil.class.getResource(AppConstants.APP_ICON)).toString());
    /**
     * 设置窗口
     */
    private static final SetStage SET_STAGE = SetStage.getInstance();

    private UiUtil() {
    }

    /**
     * 获取设置窗口。
     *
     * @return org.jcnc.jnotepad.ui.setStage.SetStage 设置窗口对象
     */
    public static SetStage getSetStage() {
        return SET_STAGE;
    }

    /**
     * 获取应用程序图标。
     *
     * @return javafx.scene.image.Image 应用程序图标对象
     */
    public static Image getIcon() {
        return ICON;
    }

    /**
     * 获取标签页布局组件。
     *
     * @return org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane 标签页布局组件对象
     * @apiNote JNotepadTabPane.getInstance()
     * @see MainTabPane
     */
    public static MainTabPane getJnotepadTabPane() {
        return TAB_PANE;
    }

    /**
     * 获取标签页组件。
     *
     * @return org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTab 标签页组件对象
     * @apiNote JNotepadTabPane.getInstance().getSelected()<br>获取当前选中的标签页
     * @see MainTabPane
     */
    public static MainTab getJnotepadtab() {
        return TAB_PANE.getSelected();
    }

    /**
     * 获取应用窗口。
     *
     * @return javafx.stage.Window 应用窗口对象
     * @apiNote JNotepadTabPane.getInstance().getSelected().getTabPane().getScene().getWindow()
     */
    public static Window getAppWindow() {
        return TAB_PANE.getSelected().getTabPane().getScene().getWindow();
    }

    /**
     * 获取视图管理组件。
     *
     * @return org.jcnc.jnotepad.view.manager.ViewManager 视图管理组件对象
     * @apiNote ViewManager.getInstance()
     */
    public static ViewManager getViewManager() {
        return VIEW_MANAGER;
    }

    /**
     * 获取状态栏组件。
     *
     * @return org.jcnc.jnotepad.root.center.main.bottom.status.JNotepadStatusBox 状态栏组件对象
     */
    public static StatusHorizontalBox getStatusBox() {
        return STATUS_BOX;
    }

    /**
     * 获取菜单栏组件。
     *
     * @return org.jcnc.jnotepad.root.top.menu.JNotepadMenuBar 菜单栏组件对象
     */
    public static JNotepadMenuBar getMenuBar() {
        return MENU_BAR;
    }
}
