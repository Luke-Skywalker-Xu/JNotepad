package org.jcnc.jnotepad.views.manager;

import org.jcnc.jnotepad.views.root.top.RootTopBorderPane;
import org.jcnc.jnotepad.views.root.top.menu.TopMenuBar;

/**
 * 顶部边界面板管理类
 *
 * @author gewuyou
 */
public class RootTopBorderPaneManager {
    private static final RootTopBorderPaneManager INSTANCE = new RootTopBorderPaneManager();

    private final RootTopBorderPane rootTopBorderPane = RootTopBorderPane.getInstance();

    private RootTopBorderPaneManager() {

    }

    public static RootTopBorderPaneManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化 RootTopBorderPane。
     *
     * <p>在顶部区域添加了 TopMenuBar 的单例实例。</p>
     */
    public void initRootBorderPane() {
        // 在顶部区域添加菜单栏
        rootTopBorderPane.setTopComponent(TopMenuBar.getInstance());
    }
}
