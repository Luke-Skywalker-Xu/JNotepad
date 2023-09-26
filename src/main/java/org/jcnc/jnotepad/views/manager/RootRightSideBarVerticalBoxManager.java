package org.jcnc.jnotepad.views.manager;

import org.jcnc.jnotepad.views.root.right.RootRightSideBarVerticalBox;

/**
 * 右侧边栏的垂直布局容器管理类
 *
 * @author gewuyou
 */
public class RootRightSideBarVerticalBoxManager {
    private static final RootRightSideBarVerticalBoxManager INSTANCE = new RootRightSideBarVerticalBoxManager();

    private final RootRightSideBarVerticalBox rootRightSideBarVerticalBox = RootRightSideBarVerticalBox.getInstance();

    private RootRightSideBarVerticalBoxManager() {
    }

    public static RootRightSideBarVerticalBoxManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化右侧边栏的垂直布局。
     */
    public void initRootRightSideBarVerticalBox() {
        // 在此添加右侧边栏布局和内容的初始化代码
    }

}
