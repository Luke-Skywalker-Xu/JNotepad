package org.jcnc.jnotepad.api.core.views.menu.builder;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.controller.config.UserConfigController;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象菜单建造者类
 *
 * @author gewuyou
 */
public abstract class AbstractMenuBuilder<B, T> {
    /**
     * 上下文菜单项
     */
    protected final Map<String, MenuItem> menuItems = new HashMap<>();

    /**
     * Get subclass builder
     *
     * @return builder
     */
    protected abstract B getBuilder();

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    protected abstract T getMenu();

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
        menuItems.put(label, menuItem);
        getItems().add(menuItem);
        return getBuilder();
    }

    /**
     * 添加菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件
     * @param visible      是否可见
     * @return 建造者
     */
    public B addMenuItem(String label, EventHandler<ActionEvent> eventHandler, BooleanProperty visible) {
        MenuItem menuItem = new MenuItem(label);
        menuItem.setOnAction(eventHandler);
        menuItem.setVisible(visible.get());
        visible.addListener((observable, oldValue, newValue) -> menuItem.setVisible(Boolean.TRUE.equals(newValue)));
        menuItems.put(label, menuItem);
        getItems().add(menuItem);
        return getBuilder();
    }

    /**
     * 添加单选菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件
     * @return 建造者
     */
    public B addRadioMenuItem(String label, EventHandler<ActionEvent> eventHandler) {
        RadioMenuItem menuItem = new RadioMenuItem(label);
        menuItem.setOnAction(eventHandler);
        menuItems.put(label, menuItem);
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
        menuItems.put(label, menuItem);
        getItems().add(menuItem);
        return getBuilder();
    }


    public B addCheckMenuItem(CheckMenuItem checkMenuItem, EventHandler<ActionEvent> eventHandler) {
        checkMenuItem.setOnAction(eventHandler);
        menuItems.put(checkMenuItem.getText(), checkMenuItem);
        getItems().add(checkMenuItem);
        return getBuilder();
    }

    /**
     * 添加菜单
     *
     * @param menu 菜单
     * @return 建造者
     */
    public B addMenu(Menu menu) {
        menuItems.put(menu.getText(), menu);
        getItems().add(menu);
        return getBuilder();
    }

    /**
     * 添加菜单
     *
     * @param menu    菜单
     * @param visible 是否隐藏
     * @return 建造者
     */
    public B addMenu(Menu menu, BooleanProperty visible) {
        menu.setVisible(visible.get());
        visible.addListener((observable, oldValue, newValue) -> menu.setVisible(Boolean.TRUE.equals(newValue)));
        menuItems.put(menu.getText(), menu);
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
     * 添加分割线
     *
     * @param visible 是否可见
     * @return 建造者
     */
    public B addSeparatorMenuItem(BooleanProperty visible) {
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        separatorMenuItem.setVisible(visible.get());
        visible.addListener((observable, oldValue, newValue) -> separatorMenuItem.setVisible(Boolean.TRUE.equals(newValue)));
        getItems().add(separatorMenuItem);
        return getBuilder();
    }

    /**
     * Build menu
     *
     * @return menu
     */
    public T build() {
        UserConfigController userConfigController = UserConfigController.getInstance();
        Map<String, MenuItem> menuItemMap = new HashMap<>(16);
        menuItems.forEach((key, value) -> {
            UiResourceBundle.bindStringProperty(value.textProperty(), key);
            menuItemMap.put((String) value.getUserData(), value);
        });
        userConfigController.getMenuItems().add(menuItemMap);
        userConfigController.initShortcutKeys(menuItemMap);
        return getMenu();
    }

}
