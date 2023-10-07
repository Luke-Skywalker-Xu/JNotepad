package org.jcnc.jnotepad.views.root.left.sidebar.tools;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;

/**
 * SidebarToolBar 是 JNotepad 应用程序的工具栏类。
 *
 * <p>该类继承自 JavaFX 的 SidebarToolBar 类，并提供了一个单例实例，用于管理工具栏上的按钮。</p>
 *
 * <p>工具栏上的按钮用于执行不同的操作，比如设置操作。</p>
 *
 * @author luke
 */
public class SidebarToolBar extends javafx.scene.control.ToolBar {
    /**
     * 单例模式，保证只有一个 JNotepadToolBar实例
     */
    private static final SidebarToolBar INSTANCE = new SidebarToolBar();

    /**
     * 工具栏上的设置按钮
     */
    Button setButton = new Button();


    /**
     * 工具栏上的文件树
     */
    Button dirTreeButton = new Button();

    /**
     * 工具栏上的运行侧边栏按钮
     */
    Button runButton = new Button();

    private SidebarToolBar() {
        // 垂直排列
        this.setOrientation(Orientation.VERTICAL);
        this.setPadding(new Insets(1,5,0,0));
        this.setOnMouseClicked(event -> {
            // SidebarToolBar 点击事件
        });
    }


    /**
     * 获取 JNotepadToolBar 的单例实例。
     *
     * @return JNotepadToolBar 的单例实例
     */
    public static SidebarToolBar getInstance() {
        return INSTANCE;
    }

    /**
     * 获取工具栏上的设置按钮。
     *
     * @return 设置按钮
     */
    public Button getSetButton() {
        return setButton;
    }

    /**
     * 获取工具栏上的文件树按钮。
     *
     * @return 文件树按钮
     */
    public Button getDirTreeButton() {
        return dirTreeButton;
    }

    /**
     * 获取工具栏上的运行按钮。
     *
     * @return 运行按钮
     */
    public Button getRunButton() {
        return runButton;
    }
}
