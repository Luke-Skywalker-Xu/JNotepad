package org.jcnc.jnotepad.root.bottom;

import org.jcnc.jnotepad.root.center.main.bottom.status.StatusHorizontalBox;
import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;

/**
 * 底部根侧边栏垂直布局
 *
 * <p>该类用于显示底部根侧边栏的垂直布局，包括状态栏等。</p>
 *
 * @author luke
 */
public class RootBottomSideBarVerticalBox extends AbstractVerticalBox {

    private static final RootBottomSideBarVerticalBox INSTANCE = new RootBottomSideBarVerticalBox();

    private RootBottomSideBarVerticalBox() {
        initSidebarVerticalBox();
    }

    private void initSidebarVerticalBox() {
        getChildren().addAll(StatusHorizontalBox.getInstance());
    }

    /**
     * 获取 RootBottomSideBarVerticalBox 的唯一实例。
     *
     * @return RootBottomSideBarVerticalBox 的实例
     */
    public static RootBottomSideBarVerticalBox getInstance() {
        return INSTANCE;
    }
}
