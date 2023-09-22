package org.jcnc.jnotepad.views.root.bottom;

import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;
import org.jcnc.jnotepad.views.root.bottom.function.FunctionBox;
import org.jcnc.jnotepad.views.root.bottom.status.BottomStatusBox;

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
    private static final VBox V_BOX_INSTANCE = new VBox();

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
    public static VBox getVboxInstance() {
        return V_BOX_INSTANCE;
    }

    private static final RootBottomSideBarVerticalBox INSTANCE = new RootBottomSideBarVerticalBox();

    public static void initSidebarVerticalBox() {
        FunctionBox functionBox = FunctionBox.getInstance();
        if (!FunctionBox.getMenuBar().getMenus().isEmpty()) {
            functionBox.getChildren().add(FunctionBox.getMenuBar());
            V_BOX_INSTANCE.getChildren().addAll(functionBox);
        }
        V_BOX_INSTANCE.getChildren().addAll(BottomStatusBox.getInstance());
        INSTANCE.getChildren().addAll(V_BOX_INSTANCE);

    }

}
