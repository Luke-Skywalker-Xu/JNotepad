package org.jcnc.jnotepad.views.root.right;

import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;

/**
 * 右侧边栏的垂直布局容器类。
 *
 * <p>该类用于管理右侧边栏的布局和内容。</p>
 *
 * @author 许轲
 */
public class RootRightSideBarVerticalBox extends AbstractVerticalBox {

    /**
     * 唯一的 RootRightSideBarVerticalBoxManager 实例，使用单例模式
     */
    private static final RootRightSideBarVerticalBox INSTANCE = new RootRightSideBarVerticalBox();

    private RootRightSideBarVerticalBox() {

    }

    /**
     * 获取 RootRightSideBarVerticalBoxManager 的唯一实例。
     *
     * @return RootRightSideBarVerticalBoxManager 的实例
     */
    public static RootRightSideBarVerticalBox getInstance() {
        return INSTANCE;
    }

}
