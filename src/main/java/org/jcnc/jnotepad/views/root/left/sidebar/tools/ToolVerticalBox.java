package org.jcnc.jnotepad.views.root.left.sidebar.tools;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.component.module.base.AbstractVerticalBox;

/**
 * 垂直布局类
 *
 * <p>用于toolbar的垂直布局</p>
 *
 * @author cccqyu
 */
public class ToolVerticalBox extends AbstractVerticalBox {
    /**
     * 单例模式，保证只有一个 ToolVBox 实例
     */
    private static final ToolVerticalBox INSTANCE = new ToolVerticalBox();

    private ToolVerticalBox() {
        // 设置子节点垂直拉伸
        VBox.setVgrow(SidebarToolBar.getInstance(), Priority.ALWAYS);

        // 将 JNotepadToolBar 添加为子节点
        getChildren().add(SidebarToolBar.getInstance());
        getStyleClass().add("tool-vertical-box");
    }

    /**
     * 获取 ToolVBox 的单例实例。
     *
     * @return ToolVBox 的单例实例
     */
    public static ToolVerticalBox getInstance() {
        return INSTANCE;
    }
}
