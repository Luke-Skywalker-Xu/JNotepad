package org.jcnc.jnotepad.ui.component.module.base;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.ui.component.module.interfaces.VerticalBoxAble;

/**
 * 抽象垂直盒子类。
 *
 * <p>这个抽象类继承自 JavaFX 的 VBox 类，实现了 VerticalBoxAble 接口，用于管理垂直排列的 UI 组件。</p>
 *
 * <p>请注意，这个类只是一个抽象类，用于提供基本的垂直盒子布局功能。您可以继承此类并添加自己的 UI 组件以构建更复杂的界面。</p>
 *
 * @author luke
 */
public abstract class AbstractVerticalBox extends VBox implements VerticalBoxAble {
    private final VBox vBox;

    /**
     * 创建一个新的 AbstractVerticalBox 实例。
     */
    public AbstractVerticalBox() {
        vBox = new VBox();
    }

    /**
     * 向垂直盒子添加一个节点。
     *
     * @param node 要添加的节点
     */
    @Override
    public void addChild(Node node) {
        vBox.getChildren().add(node);
    }

    /**
     * 向垂直盒子添加多个节点。
     *
     * @param nodes 要添加的节点数组
     */
    @Override
    public void addChild(Node... nodes) {
        vBox.getChildren().addAll(nodes);
    }

    /**
     * 获取垂直盒子的实例。
     *
     * @return 垂直盒子实例
     */
    @Override
    public VBox getVerticalBox() {
        return vBox;
    }
}
