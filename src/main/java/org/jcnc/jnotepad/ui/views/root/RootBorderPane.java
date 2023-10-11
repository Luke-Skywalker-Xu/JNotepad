package org.jcnc.jnotepad.ui.views.root;

import org.jcnc.jnotepad.ui.component.module.base.AbstractBorderPane;

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
    }

    /**
     * 获取 RootBorderPane 的单例实例。
     *
     * @return RootBorderPane 的单例实例
     */
    public static RootBorderPane getInstance() {
        return INSTANCE;
    }
}
