package org.jcnc.jnotepad.api.core.views.menu.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * 上下文菜单建造者类
 *
 * @author gewuyou
 */
public class ContextMenuBuilder extends AbstractMenuBuilder<ContextMenuBuilder, ContextMenu> {
    private final ContextMenu contextMenu;

    public ContextMenuBuilder() {
        contextMenu = new ContextMenu();
    }


    /**
     * Builds and returns the ContextMenu object.
     *
     * @return the built ContextMenu object
     */
    @Override
    public ContextMenu build() {
        return contextMenu;
    }

    /**
     * Get subclass builder
     *
     * @return builder
     */
    @Override
    protected ContextMenuBuilder getBuilder() {
        return this;
    }

    /**
     * Retrieves the items of the menu.
     *
     * @return an ObservableList of MenuItems
     */
    @Override
    protected ObservableList<MenuItem> getItems() {
        return contextMenu.getItems();
    }
}
