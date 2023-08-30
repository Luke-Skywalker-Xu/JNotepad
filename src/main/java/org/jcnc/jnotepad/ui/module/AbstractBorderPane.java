package org.jcnc.jnotepad.ui.module;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.Interface.BorderPaneAble;

public abstract class AbstractBorderPane extends BorderPane implements BorderPaneAble {

    @Override
    public void setTopComponent(Node node) {
        setTop(node);
    }

    @Override
    public void setBottomComponent(Node node) {
        setBottom(node);
    }

    @Override
    public void setLeftComponent(Node node) {
        setLeft(node);
    }

    @Override
    public void setRightComponent(Node node) {
        setRight(node);
    }

    @Override
    public void setCenterComponent(Node node) {
        setCenter(node);
    }

}
