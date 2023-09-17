package org.jcnc.jnotepad.views.root.bottom;

import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;
import org.jcnc.jnotepad.views.root.bottom.cmd.CmdStatusBox;
import org.jcnc.jnotepad.views.root.bottom.status.BottomStatusBox;

/**
 * 底部根侧边栏垂直布局
 *
 * <p>该类用于显示底部根侧边栏的垂直布局，包括状态栏等。</p>
 *
 * @author luke
 */
public class RootBottomSideBarVerticalBox extends AbstractVerticalBox {

    VBox bottomSideBarVerticalBox;

    /**
     * 获取 RootBottomSideBarVerticalBox 的唯一实例。
     *
     * @return RootBottomSideBarVerticalBox 的实例
     */
    public static RootBottomSideBarVerticalBox getInstance() {
        return INSTANCE;
    }

    public void initSidebarVerticalBox() {
        bottomSideBarVerticalBox = new VBox();

        bottomSideBarVerticalBox.getChildren().addAll(CmdStatusBox.getInstance(), BottomStatusBox.getInstance());


        getChildren().addAll(bottomSideBarVerticalBox);

    }

    private static final RootBottomSideBarVerticalBox INSTANCE = new RootBottomSideBarVerticalBox();

    public RootBottomSideBarVerticalBox() {
        initSidebarVerticalBox();
    }

}
