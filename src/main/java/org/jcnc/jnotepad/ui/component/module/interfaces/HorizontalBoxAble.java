package org.jcnc.jnotepad.ui.component.module.interfaces;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * 可添加子节点的水平布局接口。
 *
 * <p>该接口定义了向水平布局添加子节点和获取水平布局的方法。</p>
 *
 * <p>实现此接口的类可以使用方法将一个或多个子节点添加到水平布局中，以自定义水平布局的UI组件。</p>
 *
 * @author luke
 */
public interface HorizontalBoxAble {
    /**
     * 添加一个或多个子节点到水平布局中。
     *
     * @param node 要添加的子节点
     */
    void addChild(Node node);

    /**
     * 添加一个或多个子节点到水平布局中。
     *
     * @param nodes 要添加的子节点数组
     */
    void addChild(Node... nodes);

    /**
     * 获取水平布局。
     *
     * @return 水平布局
     */
    HBox getHorizontalBox();
}
