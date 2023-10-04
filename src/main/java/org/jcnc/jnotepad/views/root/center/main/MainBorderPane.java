package org.jcnc.jnotepad.views.root.center.main;

import org.jcnc.jnotepad.component.module.base.AbstractBorderPane;

/**
 * 主界面边界布局
 *
 * <p>该类用于显示文本框及其周边组件，作为主界面的中心区域。</p>
 *
 * @author 许轲
 */
public class MainBorderPane extends AbstractBorderPane {

    private static final MainBorderPane INSTANCE = new MainBorderPane();

    private MainBorderPane() {

    }

    /**
     * 获取 MainBorderPane 的唯一实例。
     *
     * @return MainBorderPane 的实例
     */
    public static MainBorderPane getInstance() {
        return INSTANCE;
    }

}
