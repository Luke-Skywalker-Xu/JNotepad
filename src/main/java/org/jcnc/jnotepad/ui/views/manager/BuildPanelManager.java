package org.jcnc.jnotepad.ui.views.manager;

import javafx.scene.control.SplitPane;
import org.jcnc.jnotepad.ui.component.module.TextCodeArea;
import org.jcnc.jnotepad.ui.views.root.center.main.MainBorderPane;

/**
 * 构建底部三大菜单管理类
 *
 * <p>管理构建三大菜单操作</p>
 *
 * @author cccqyu
 */
public class BuildPanelManager {

    private BuildPanelManager() {
    }

    /**
     * 单例模式，保证只有一个 BuildPanelManager 实例
     */
    private static final BuildPanelManager INSTANCE = new BuildPanelManager();


    public static BuildPanelManager getInstance() {
        return INSTANCE;
    }


    private static final MainBorderPane MAIN_BORDER_PANE = MainBorderPane.getInstance();
    private boolean isShow = false;

    /**
     * 控制终端显示
     */
    public void controlShow() {

        // 获取root分割面板
        SplitPane root = (SplitPane) MAIN_BORDER_PANE.getCenter();

        if (isShow) {
            root.setDividerPositions(1);
        } else {
            // 展开分割条，文件树
            root.setDividerPositions(0.7);
        }
        isShow = !isShow;
    }

    public void controlShow(boolean bool) {

        // 获取root分割面板
        SplitPane root = (SplitPane) MAIN_BORDER_PANE.getCenter();

        if (!bool) {
            root.setDividerPositions(1);
        } else {
            // 展开分割条，文件树
            root.setDividerPositions(0.7);
        }
        isShow = !isShow;
    }

    public void setText(TextCodeArea textCodeArea, String text) {
        textCodeArea.appendText(text);
    }

}
