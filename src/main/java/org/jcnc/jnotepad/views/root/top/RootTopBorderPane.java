package org.jcnc.jnotepad.views.root.top;

import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

/**
 * RootTopBorderPane 类表示 JNotepad 应用程序的顶部边界面板。
 *
 * <p>该边界面板用于显示文本框以及文本框周边的内容。</p>
 *
 * @author 许轲
 */
public class RootTopBorderPane extends AbstractBorderPane {
    /**
     * 单例模式，保证只有一个 RootTopBorderPane 实例
     */
    private static final RootTopBorderPane INSTANCE = new RootTopBorderPane();

    private RootTopBorderPane() {

    }

    /**
     * 获取 RootTopBorderPane 的单例实例。
     *
     * @return RootTopBorderPane 的单例实例
     */
    public static RootTopBorderPane getInstance() {
        return INSTANCE;
    }


}
