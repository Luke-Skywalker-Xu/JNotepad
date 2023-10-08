package org.jcnc.jnotepad.api.core.views.menu.builder;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

/**
 * 抽象菜单建造者类
 *
 * @author gewuyou
 */
public abstract class AbstractMenuBuilder<B, T> {
    /**
     * Get subclass builder
     *
     * @return builder
     */
    protected abstract B getBuilder();

    /**
     * Retrieves the items of the menu.
     *
     * @return an ObservableList of MenuItems
     */
    protected abstract ObservableList<MenuItem> getItems();

    /**
     * 添加菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件
     * @return 建造者
     */
    public B addMenuItem(String label, EventHandler<ActionEvent> eventHandler) {
        MenuItem menuItem = new MenuItem(label);
        menuItem.setOnAction(eventHandler);
        getItems().add(menuItem);
        return getBuilder();
    }

    /**
     * 添加菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件
     * @param disable      是否禁用
     * @return 建造者
     */
    public B addMenuItem(String label, EventHandler<ActionEvent> eventHandler, boolean disable) {
        if (!disable) {
            return getBuilder();
        }
        MenuItem menuItem = new MenuItem(label);
        menuItem.setOnAction(eventHandler);
        getItems().add(menuItem);
        return getBuilder();
    }

    /**
     * 添加单选菜单项 todo 待完善
     *
     * @param label        菜单项名称
     * @param eventHandler 事件
     * @return 建造者
     */
    public B addRadioMenuItem(String label, EventHandler<ActionEvent> eventHandler) {
        RadioMenuItem menuItem = new RadioMenuItem(label);
        menuItem.setOnAction(eventHandler);
        getItems().add(menuItem);
        return getBuilder();
    }

    /**
     * 添加复选菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件
     * @return 建造者
     */
    public B addCheckMenuItem(String label, EventHandler<ActionEvent> eventHandler) {
        CheckMenuItem menuItem = new CheckMenuItem(label);
        menuItem.setOnAction(eventHandler);
        getItems().add(menuItem);
        return getBuilder();
    }

    /**
     * 添加菜单
     *
     * @param menu 菜单
     * @return 建造者
     */
    public B addMenu(Menu menu) {
        getItems().add(menu);
        return getBuilder();
    }

    /**
     * 添加菜单
     *
     * @param menu    菜单
     * @param disable 是否禁用
     * @return 建造者
     */
    public B addMenu(Menu menu, boolean disable) {
        if (!disable) {
            return getBuilder();
        }
        getItems().add(menu);
        return getBuilder();
    }

    /**
     * 添加分割线
     *
     * @return 建造者
     */
    public B addSeparatorMenuItem() {
        getItems().add(new SeparatorMenuItem());
        return getBuilder();
    }

    /**
     * Build menu
     *
     * @return menu
     */
    public abstract T build();

}
