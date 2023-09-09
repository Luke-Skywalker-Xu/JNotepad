package org.jcnc.jnotepad.views.root;

import org.jcnc.jnotepad.ui.module.AbstractBorderPane;
import org.jcnc.jnotepad.views.root.bottom.RootBottomSideBarVerticalBox;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.ToolHorizontalBox;
import org.jcnc.jnotepad.views.root.right.RootRightSideBarVerticalBox;
import org.jcnc.jnotepad.views.root.top.RootTopBorderPane;

/**
 * RootBorderPane 表示 JNotepad 应用程序的根布局。
 *
 * <p>该布局包含了应用程序的主要组件，包括主界面、工具栏、侧边栏、菜单栏等。</p>
 *
 * @author 许轲
 */
public class RootBorderPane extends AbstractBorderPane {
    /**
     * 单例模式，保证只有一个 RootBorderPane 实例
     */
    private static final RootBorderPane INSTANCE = new RootBorderPane();

    private RootBorderPane() {
        initRootBorderPane();
    }

    /**
     * 获取 RootBorderPane 的单例实例。
     *
     * @return RootBorderPane 的单例实例
     */
    public static RootBorderPane getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化 RootBorderPane。
     *
     * <p>设置主界面(MainBorderPane)、工具栏(ToolHBox)、侧边栏(RootRightSideBarVBox)、
     * 菜单栏(RootTopBorderPane)以及底部边栏(RootBottomSideBarVBox)等主要组件。</p>
     */
    private void initRootBorderPane() {
        // 中间，用于显示主界面
        setCenterComponent(MainBorderPane.getInstance());
        // 主界面的左边，工具栏
        setLeftComponent(ToolHorizontalBox.getInstance());
        // 主界面的右边，侧边栏
        setRightComponent(RootRightSideBarVerticalBox.getInstance());
        // 主界面的上面，菜单栏
        setTopComponent(RootTopBorderPane.getInstance());
        // 主界面的下面，底部边栏
        setBottomComponent(RootBottomSideBarVerticalBox.getInstance());
    }


}
