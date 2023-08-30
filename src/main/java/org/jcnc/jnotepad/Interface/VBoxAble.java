package org.jcnc.jnotepad.Interface;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public interface VBoxAble {
    void addChild(Node node);

    void addChild(Node... nodes);

    VBox getVBox();
}
