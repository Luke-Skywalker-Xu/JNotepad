package org.jcnc.jnotepad.views.manager;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import org.jcnc.jnotepad.common.constants.SplitPaneItemConstants;
import org.jcnc.jnotepad.component.module.vbox.BuildPanel;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.center.main.center.directory.DirectorySidebarPane;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;

/**
 * 主界面边界布局
 *
 * @author gewuyou
 */
public class MainBorderPaneManager {
    private static final MainBorderPaneManager INSTANCE = new MainBorderPaneManager();

    private static final MainBorderPane MAIN_BORDER_PANE = MainBorderPane.getInstance();
    private static final DirectorySidebarPane DIRECTORY_SIDEBAR_PANE = DirectorySidebarPane.getInstance();

    /**
     * 默认分割条位置
     */
    private static final double TOP_SPLIT_PANEL_DEFAULT_DIVIDER_POSITIONS = 0.3;

    private MainBorderPaneManager() {

    }

    public static MainBorderPaneManager getInstance() {
        return INSTANCE;
    }

    public void initMainBorderPane() {
        // 总分割面板
        SplitPane rootSplitPane = new SplitPane();
        // 设置上下布局
        rootSplitPane.setOrientation(Orientation.VERTICAL);
        rootSplitPane.setDividerPositions(1);


        // 上部面板
        // 文件树和文本框的布局
        SplitPane topSplitPane = new SplitPane();
        topSplitPane.getItems().add(SplitPaneItemConstants.TOP_SPLIT_PANE_DIRECTORY_SIDEBAR_PANE, DIRECTORY_SIDEBAR_PANE);
        topSplitPane.getItems().add(SplitPaneItemConstants.TOP_SPLIT_PANE_CENTER_TAB_PANE, CenterTabPane.getInstance());
        topSplitPane.setDividerPositions(TOP_SPLIT_PANEL_DEFAULT_DIVIDER_POSITIONS);

        // 下部
        // 构建三大菜单
        BuildPanel buildPanel = BuildPanel.getInstance();

        rootSplitPane.getItems().add(SplitPaneItemConstants.ROOT_SPLIT_PANE_TOP_SPLIT_PANE, topSplitPane);
        rootSplitPane.getItems().add(SplitPaneItemConstants.TOP_SPLIT_PANE_CENTER_TAB_PANE, buildPanel);

        // 将总分割面板设置在布局中部
        MAIN_BORDER_PANE.setCenterComponent(rootSplitPane);


    }
}
