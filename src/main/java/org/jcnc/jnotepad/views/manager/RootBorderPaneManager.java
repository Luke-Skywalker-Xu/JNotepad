package org.jcnc.jnotepad.views.manager;

import org.jcnc.jnotepad.views.root.RootBorderPane;
import org.jcnc.jnotepad.views.root.bottom.RootBottomSideBarVerticalBox;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.ToolHorizontalBox;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.ToolVerticalBox;
import org.jcnc.jnotepad.views.root.right.RootRightSideBarVerticalBox;
import org.jcnc.jnotepad.views.root.top.RootTopBorderPane;

/**
 * 应用程序的根布局管理类
 *
 * @author gewuyou
 */
public class RootBorderPaneManager {

    private static final RootBorderPaneManager INSTANCE = new RootBorderPaneManager();

    private final RootBorderPane rootBorderPane = RootBorderPane.getInstance();

    private RootBorderPaneManager() {

    }

    public static RootBorderPaneManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化 RootBorderPane。
     *
     * <p>设置主界面(MainBorderPane)、工具栏(ToolHBox)、侧边栏(RootRightSideBarVBox)、
     * 菜单栏(RootTopBorderPane)以及底部边栏(RootBottomSideBarVBox)等主要组件。</p>
     */
    public void initRootBorderPane() {
        // 中间，用于显示主界面
        rootBorderPane.setCenterComponent(MainBorderPane.getInstance());
        // 主界面的左边，工具栏
//        rootBorderPane.setLeftComponent(ToolHorizontalBox.getInstance());
        rootBorderPane.setLeftComponent(ToolVerticalBox.getInstance());
        // 主界面的右边，侧边栏
        rootBorderPane.setRightComponent(RootRightSideBarVerticalBox.getInstance());
        // 主界面的上面，菜单栏
        rootBorderPane.setTopComponent(RootTopBorderPane.getInstance());
        // 主界面的下面，底部边栏
        rootBorderPane.setBottomComponent(RootBottomSideBarVerticalBox.getInstance());

    }
}
