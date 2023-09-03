package org.jcnc.jnotepad.root.left.sidebar.tools;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.jcnc.jnotepad.ui.module.AbstractHorizontalBox;

/**
 * ToolHBox 是 JNotepad 应用程序的工具栏水平布局类。
 *
 * <p>该类继承自 JavaFX 的 HBox 类，用于将工具栏放置在水平布局中。</p>
 *
 * <p>工具栏水平布局中包含一个 JNotepadToolBar 的单例实例，并设置其子节点水平拉伸属性，以确保工具栏在水平方向上充分占用空间。</p>
 *
 * @author luke
 */
public class ToolHorizontalBox extends AbstractHorizontalBox {
    /**
     * 单例模式，保证只有一个 ToolHBox 实例
     */
    private static final ToolHorizontalBox INSTANCE = new ToolHorizontalBox();

    private ToolHorizontalBox() {
        // 设置子节点水平拉伸
        HBox.setHgrow(SidebarToolBar.getInstance(), Priority.ALWAYS);

        // 将 JNotepadToolBar 添加为子节点
        getChildren().add(SidebarToolBar.getInstance());
    }

    /**
     * 获取 ToolHBox 的单例实例。
     *
     * @return ToolHBox 的单例实例
     */
    public static ToolHorizontalBox getInstance() {
        return INSTANCE;
    }
}
