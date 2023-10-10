package org.jcnc.jnotepad.component.module.base;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.component.module.interfaces.BorderPaneAble;

/**
 * 抽象边界面板类。
 *
 * <p>这个抽象类继承自 JavaFX 的 BorderPane 类，实现了 BorderPaneAble 接口，用于管理 UI 组件的布局。</p>
 *
 * @author luke
 */
public abstract class AbstractBorderPane extends BorderPane implements BorderPaneAble {

    /**
     * 设置顶部组件。
     *
     * @param node 顶部组件
     */
    @Override
    public void setTopComponent(Node node) {
        setTop(node);
    }

    /**
     * 设置底部组件。
     *
     * @param node 底部组件
     */
    @Override
    public void setBottomComponent(Node node) {
        setBottom(node);
    }

    /**
     * 设置左侧组件。
     *
     * @param node 左侧组件
     */
    @Override
    public void setLeftComponent(Node node) {
        setLeft(node);
    }

    /**
     * 设置右侧组件。
     *
     * @param node 右侧组件
     */
    @Override
    public void setRightComponent(Node node) {
        setRight(node);
    }

    /**
     * 设置中心组件。
     *
     * @param node 中心组件
     */
    @Override
    public void setCenterComponent(Node node) {
        setCenter(node);
    }
}
