package org.jcnc.jnotepad.ui.module;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.common.interfaces.VerticalBoxAble;

/**
 * 抽象垂直盒子类
 *
 * <p>这个抽象类继承自JavaFX的VBox类，实现了VBoxAble接口，用于管理垂直排列的UI组件。</p>
 *
 * @author luke
 */
public abstract class AbstractVerticalBox extends VBox implements VerticalBoxAble {

    private final VBox vBox;

    public AbstractVerticalBox() {
        vBox = new VBox();
    }

    @Override
    public void addChild(Node node) {
        vBox.getChildren().add(node);
    }

    @Override
    public void addChild(Node... nodes) {
        vBox.getChildren().addAll(nodes);
    }

    @Override
    public VBox getVerticalBox() {
        return vBox;
    }
}
