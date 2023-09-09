package org.jcnc.jnotepad.views.root.right;

import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;

/**
 * 右侧边栏的垂直布局容器类。
 *
 * <p>该类用于管理右侧边栏的布局和内容。</p>
 *
 * @Author 许轲
 */
public class RootRightSideBarVerticalBox extends AbstractVerticalBox {

    /**
     * 唯一的 RootRightSideBarVerticalBox 实例，使用单例模式
     */
    private static final RootRightSideBarVerticalBox INSTANCE = new RootRightSideBarVerticalBox();

    private RootRightSideBarVerticalBox() {
        initSidebarVerticalBox();
    }

    /**
     * 获取 RootRightSideBarVerticalBox 的唯一实例。
     *
     * @return RootRightSideBarVerticalBox 的实例
     */
    public static RootRightSideBarVerticalBox getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化右侧边栏的垂直布局。
     */
    private void initSidebarVerticalBox() {
        // 在此添加右侧边栏布局和内容的初始化代码
    }
}
