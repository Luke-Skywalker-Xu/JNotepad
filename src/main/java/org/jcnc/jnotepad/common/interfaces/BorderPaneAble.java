package org.jcnc.jnotepad.common.interfaces;

import javafx.scene.Node;

/**
 * 可设置BorderPane子组件的接口
 *
 * <p>该接口定义了设置BorderPane的各个子组件（上、下、左、右、中）的方法。</p>
 *
 * @author luke
 */
public interface BorderPaneAble {

    /**
     * 设置BorderPane的顶部组件。
     *
     * @param node 要设置为顶部组件的节点
     */
    void setTopComponent(Node node);

    /**
     * 设置BorderPane的底部组件。
     *
     * @param node 要设置为底部组件的节点
     */
    void setBottomComponent(Node node);

    /**
     * 设置BorderPane的左侧组件。
     *
     * @param node 要设置为左侧组件的节点
     */
    void setLeftComponent(Node node);

    /**
     * 设置BorderPane的右侧组件。
     *
     * @param node 要设置为右侧组件的节点
     */
    void setRightComponent(Node node);

    /**
     * 设置BorderPane的中间组件。
     *
     * @param node 要设置为中间组件的节点
     */
    void setCenterComponent(Node node);
}
