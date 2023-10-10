package org.jcnc.jnotepad.component.module.base;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.jcnc.jnotepad.component.module.interfaces.HorizontalBoxAble;

/**
 * 抽象水平盒子类。
 *
 * <p>这个抽象类继承自 JavaFX 的 HBox 类，实现了 HorizontalBoxAble 接口，用于管理水平排列的 UI 组件。</p>
 *
 * <p>请注意，这个类只是一个抽象类，用于提供基本的水平盒子布局功能。您可以继承此类并添加自己的 UI 组件以构建更复杂的界面。</p>
 *
 * @author luke
 */
public abstract class AbstractHorizontalBox extends HBox implements HorizontalBoxAble {
    private final HBox hBox;

    /**
     * 创建一个新的 AbstractHorizontalBox 实例。
     */
    public AbstractHorizontalBox() {
        hBox = new HBox();
    }

    /**
     * 向水平盒子添加一个节点。
     *
     * @param node 要添加的节点
     */
    @Override
    public void addChild(Node node) {
        hBox.getChildren().add(node);
    }

    /**
     * 向水平盒子添加多个节点。
     *
     * @param nodes 要添加的节点数组
     */
    @Override
    public void addChild(Node... nodes) {
        hBox.getChildren().addAll(nodes);
    }

    /**
     * 获取水平盒子的实例。
     *
     * @return 水平盒子实例
     */
    @Override
    public HBox getHorizontalBox() {
        return hBox;
    }
}
