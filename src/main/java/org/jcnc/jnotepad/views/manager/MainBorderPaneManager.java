package org.jcnc.jnotepad.views.manager;

import javafx.scene.control.SplitPane;
import org.jcnc.jnotepad.views.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.views.root.center.main.center.directory.DirectorySidebarPane;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;

/**
 * 主界面边界布局
 *
 * @author gewuyou
 */
public class MainBorderPaneManager {
    private static final MainBorderPaneManager INSTANCE = new MainBorderPaneManager();

    private static final MainBorderPane MAIN_BORDER_PANE = MainBorderPane.getInstance();
    private static final DirectorySidebarPane DIRECTORY_SIDEBAR_PANE = DirectorySidebarPane.getInstance();

    // 默认分割条位置
    private static final double defaultDividerPositions = 0.3;
    private MainBorderPaneManager() {

    }

    public static MainBorderPaneManager getInstance() {
        return INSTANCE;
    }

    public void initMainBorderPane() {
        // 文件树和文本框的布局
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(0, DIRECTORY_SIDEBAR_PANE);
        splitPane.getItems().add(1, CenterTabPane.getInstance());
        splitPane.setDividerPositions(defaultDividerPositions);

        // 将文件树以及文本框设置在布局中部
        MAIN_BORDER_PANE.setCenterComponent(splitPane);
    }
}
