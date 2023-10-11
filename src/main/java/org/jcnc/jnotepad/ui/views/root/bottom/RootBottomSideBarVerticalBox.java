package org.jcnc.jnotepad.ui.views.root.bottom;

import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.component.module.base.AbstractVerticalBox;

/**
 * 底部根侧边栏垂直布局
 *
 * <p>该类用于显示底部根侧边栏的垂直布局，包括状态栏等。</p>
 *
 * @author luke
 */
public class RootBottomSideBarVerticalBox extends AbstractVerticalBox {

    /**
     * VBox实例
     */
    private final VBox vBox = new VBox();

    /**
     * 获取 RootBottomSideBarVerticalBox 的唯一实例。
     *
     * @return RootBottomSideBarVerticalBox 的实例
     */
    public static RootBottomSideBarVerticalBox getInstance() {
        return INSTANCE;
    }

    private RootBottomSideBarVerticalBox() {

    }

    /**
     * 获取vbox实例
     *
     * @return VBox
     */
    public VBox getVbox() {
        return vBox;
    }

    private static final RootBottomSideBarVerticalBox INSTANCE = new RootBottomSideBarVerticalBox();

}
