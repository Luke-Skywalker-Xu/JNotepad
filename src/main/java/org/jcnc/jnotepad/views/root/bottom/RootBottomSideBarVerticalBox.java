package org.jcnc.jnotepad.views.root.bottom;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;
import org.jcnc.jnotepad.views.root.bottom.cmd.CmdStatusBox;
import org.jcnc.jnotepad.views.root.bottom.status.BottomStatusBox;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.SidebarToolBar;

/**
 * 底部根侧边栏垂直布局
 *
 * <p>该类用于显示底部根侧边栏的垂直布局，包括状态栏等。</p>
 *
 * @author luke
 */
public class RootBottomSideBarVerticalBox extends AbstractVerticalBox {

    /**
     * 获取 RootBottomSideBarVerticalBox 的唯一实例。
     *
     * @return RootBottomSideBarVerticalBox 的实例
     */
    public static RootBottomSideBarVerticalBox getInstance() {
        return INSTANCE;
    }

    private void initSidebarVerticalBox() {
        VBox bottomSideBarVerticalBox = new VBox();

        bottomSideBarVerticalBox.getChildren().addAll(CmdStatusBox.getInstance(), BottomStatusBox.getInstance());


        getChildren().addAll(bottomSideBarVerticalBox);

    }

    private static final RootBottomSideBarVerticalBox INSTANCE = new RootBottomSideBarVerticalBox();

    private RootBottomSideBarVerticalBox() {
        initSidebarVerticalBox();
    }

}
