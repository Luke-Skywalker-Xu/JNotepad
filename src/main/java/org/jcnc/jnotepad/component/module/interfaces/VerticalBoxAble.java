package org.jcnc.jnotepad.component.module.interfaces;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * 可添加子节点的垂直布局接口。
 *
 * <p>该接口定义了向垂直布局添加子节点和获取垂直布局的方法。</p>
 *
 * <p>实现此接口的类可以使用方法将一个或多个子节点添加到垂直布局中，以自定义垂直布局的UI组件。</p>
 *
 * @author luke
 */
public interface VerticalBoxAble {
    /**
     * 添加一个或多个子节点到垂直布局中。
     *
     * @param node 要添加的子节点
     */
    void addChild(Node node);

    /**
     * 添加一个或多个子节点到垂直布局中。
     *
     * @param nodes 要添加的子节点数组
     */
    void addChild(Node... nodes);

    /**
     * 获取垂直布局。
     *
     * @return 垂直布局
     */
    VBox getVerticalBox();
}
