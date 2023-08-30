package org.jcnc.jnotepad.ui.module;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.Interface.VBoxAble;

public abstract class AbstractVBox extends VBox implements VBoxAble {

    private final VBox vBox;

    public AbstractVBox() {
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
    public VBox getVBox() {
        return vBox;
    }
}
