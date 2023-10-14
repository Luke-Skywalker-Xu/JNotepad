package org.jcnc.jnotepad.api.core.views.menu.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * 上下文菜单建造者类
 *
 * <p>
 * 此类用于构建上下文菜单对象，可以添加菜单项、单选菜单项、复选菜单项以及分割线等。
 * </p>
 *
 * @author gewuyou
 */
public class ContextMenuBuilder extends AbstractMenuBuilder<ContextMenuBuilder, ContextMenu> {

    private final ContextMenu contextMenu;

    public ContextMenuBuilder() {
        contextMenu = new ContextMenu();
    }

    /**
     * 获取子类的建造者实例
     *
     * @return 建造者实例
     */
    @Override
    protected ContextMenuBuilder getBuilder() {
        return this;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    protected ContextMenu getMenu() {
        return contextMenu;
    }

    /**
     * 获取上下文菜单的菜单项列表
     *
     * @return 菜单项列表
     */
    @Override
    protected ObservableList<MenuItem> getItems() {
        return contextMenu.getItems();
    }
}
