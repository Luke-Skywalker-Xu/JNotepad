package org.jcnc.jnotepad.ui.module;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.jcnc.jnotepad.Interface.HBoxAble;

public abstract class AbstractHBox extends HBox implements HBoxAble {
    private final HBox hBox;

    public AbstractHBox() {
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
    public HBox getHBox() {
        return hBox;
    }
}
