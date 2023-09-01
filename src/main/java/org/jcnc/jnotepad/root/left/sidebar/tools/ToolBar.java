package org.jcnc.jnotepad.root.left.sidebar.tools;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JNotepadToolBar 是 JNotepad 应用程序的工具栏类。
 *
 * <p>该类继承自 JavaFX 的 ToolBar 类，并提供了一个单例实例，用于管理工具栏上的按钮。</p>
 *
 * <p>工具栏上的按钮用于执行不同的操作，比如设置操作。</p>
 *
 * @author luke
 */
public class ToolBar extends javafx.scene.control.ToolBar {
    /**
     * 单例模式，保证只有一个 JNotepadToolBar实例
     */
    private static final ToolBar INSTANCE = new ToolBar();

    /**
     * 工具栏上的设置按钮
     */
    Button setButton = new Button();

    private ToolBar() {
        // 创建工具栏上的按钮
        Image image = new Image("tools.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(10);
        imageView.setFitHeight(10);
        // 设置水平缩放比例
        imageView.setScaleX(2.5);
        // 设置垂直缩放比例
        imageView.setScaleY(2.5);
        // 设置缩放比例
        setButton.setGraphic(imageView);

        // 将按钮添加到工具栏
        getItems().addAll(setButton);
    }

    /**
     * 获取 JNotepadToolBar 的单例实例。
     *
     * @return JNotepadToolBar 的单例实例
     */
    public static ToolBar getInstance() {
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
}
