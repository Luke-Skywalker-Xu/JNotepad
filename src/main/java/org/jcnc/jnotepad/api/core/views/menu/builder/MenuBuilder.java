package org.jcnc.jnotepad.api.core.views.menu.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * 菜单建造者类
 *
 * @author gewuyou
 */
public class MenuBuilder extends AbstractMenuBuilder<MenuBuilder, Menu> {
    private final Menu menu;

    public MenuBuilder(String label) {
        menu = new Menu(label);
    }

    /**
     * Get subclass builder
     *
     * @return builder
     */
    @Override
    protected MenuBuilder getBuilder() {
        return this;
    }

    /**
     * Retrieves the items of the menu.
     *
     * @return an ObservableList of MenuItems
     */
    @Override
    protected ObservableList<MenuItem> getItems() {
        return menu.getItems();
    }

    /**
     * Build menu
     *
     * @return menu
     */
    @Override
    public Menu build() {
        return menu;
    }
}
