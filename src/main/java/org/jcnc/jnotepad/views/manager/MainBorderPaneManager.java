package org.jcnc.jnotepad.views.manager;

import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;

/**
 * 主界面边界布局
 *
 * @author gewuyou
 */
public class MainBorderPaneManager {
    private static final MainBorderPaneManager INSTANCE = new MainBorderPaneManager();

    private static final MainBorderPane MAIN_BORDER_PANE = MainBorderPane.getInstance();

    private MainBorderPaneManager() {

    }

    public static MainBorderPaneManager getInstance() {
        return INSTANCE;
    }

    public void initMainBorderPane() {
        // 文本框
        MAIN_BORDER_PANE.setCenterComponent(CenterTabPane.getInstance());
    }
}
