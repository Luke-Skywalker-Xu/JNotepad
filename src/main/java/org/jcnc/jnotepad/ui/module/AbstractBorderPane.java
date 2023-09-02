package org.jcnc.jnotepad.ui.module;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.interfaces.BorderPaneAble;

/**
 * 抽象边界面板类
 *
 * <p>这个抽象类继承自JavaFX的BorderPane类，实现了BorderPaneAble接口，用于管理UI组件的布局。</p>
 *
 * @author luke
 */
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
