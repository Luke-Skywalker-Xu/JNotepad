package org.jcnc.jnotepad.ui.views.manager;

import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.views.root.bottom.RootBottomSideBarVerticalBox;
import org.jcnc.jnotepad.ui.views.root.bottom.function.FunctionBox;
import org.jcnc.jnotepad.ui.views.root.bottom.status.BottomStatusBox;

/**
 * 底部根侧边栏垂直布局管理类
 *
 * @author gewuyou
 */
public class RootBottomSideBarVerticalBoxManager {
    private static final RootBottomSideBarVerticalBoxManager INSTANCE = new RootBottomSideBarVerticalBoxManager();

    private final RootBottomSideBarVerticalBox rootBottomSideBarVerticalBox = RootBottomSideBarVerticalBox.getInstance();

    private RootBottomSideBarVerticalBoxManager() {

    }

    public static RootBottomSideBarVerticalBoxManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化底部根侧边栏垂直布局
     */

    public void initSidebarVerticalBox() {
        FunctionBox functionBox = FunctionBox.getInstance();
        VBox vbox = rootBottomSideBarVerticalBox.getVbox();
        if (!FunctionBox.getMenuBar().getMenus().isEmpty()) {
            functionBox.getChildren().add(FunctionBox.getMenuBar());
            vbox.getChildren().addAll(functionBox);
        }
        vbox.getChildren().addAll(BottomStatusBox.getInstance());
        rootBottomSideBarVerticalBox.getChildren().addAll(vbox);

    }
}
