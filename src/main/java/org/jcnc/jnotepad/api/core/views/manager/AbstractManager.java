package org.jcnc.jnotepad.api.core.views.manager;

import java.util.List;

/**
 * 抽象管理类
 *
 * @author gewuyou
 */
public abstract class AbstractManager<T> {

    /**
     * 获取节点列表
     *
     * @return 节点列表
     */
    public abstract List<T> getNodeList();

    public void registerNode(T node) {
        getNodeList().add(node);
    }
}
