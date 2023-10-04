package org.jcnc.jnotepad.component.module.base;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.jcnc.jnotepad.component.module.interfaces.HorizontalBoxAble;

/**
 * 抽象水平盒子类
 *
 * <p>这个抽象类继承自JavaFX的HBox类，实现了HBoxAble接口，用于管理水平排列的UI组件。</p>
 *
 * @author luke
 */
public abstract class AbstractHorizontalBox extends HBox implements HorizontalBoxAble {
    private final HBox hBox;

    public AbstractHorizontalBox() {
        hBox = new HBox();
    }

    @Override
    public void addChild(Node node) {
        hBox.getChildren().add(node);
    }

    @Override
    public void addChild(Node... nodes) {
        hBox.getChildren().addAll(nodes);
    }

    @Override
    public HBox getHorizontalBox() {
        return hBox;
    }
}
