package org.jcnc.jnotepad.Interface;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public interface HBoxAble {

    void addChild(Node node);

    void addChild(Node... nodes);
    HBox getHBox();
}
