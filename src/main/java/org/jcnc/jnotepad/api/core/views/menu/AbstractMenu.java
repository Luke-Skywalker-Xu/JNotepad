package org.jcnc.jnotepad.api.core.views.menu;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import org.jcnc.jnotepad.app.utils.LogUtil;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 抽象菜单类
 *
 * @author gewuyou
 */
public abstract class AbstractMenu<T> {
    protected Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    public abstract T getMenu();

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    public abstract Map<String, MenuItem> getMenuItems();

    /**
     * 注册菜单
     */
    protected abstract void registerMenu();

    /**
     * 初始化菜单
     */
    protected abstract void initMenu();


    /**
     * 注册菜单项
     *
     * @param menuItem     菜单项
     * @param menuItemName 菜单项名称
     * @param userData     用户数据，用来存放必要的数据，比如按钮菜单项名称
     * @param eventHandler 事件处理器
     */
    public void registerMenuItem(MenuItem menuItem, String menuItemName, Object userData, EventHandler<ActionEvent> eventHandler) {
        getMenuItems().put(menuItemName, menuItem);
        menuItem.setUserData(userData);
        menuItem.setOnAction(eventHandler);
    }

    /**
     * 注册菜单项
     *
     * @param menuItem     菜单项
     * @param menuItemName 菜单项名称
     * @param userData     用户数据，用来存放必要的数据，比如按钮菜单项名称
     * @param eventHandler 事件处理器
     */
    public void registerMenuItem(MenuItem menuItem, String menuItemName, Object userData, EventHandler<ActionEvent> eventHandler, boolean isDisable) {
        if (!isDisable) {
            registerMenuItem(menuItem, menuItemName, userData, eventHandler);
        }
    }

    /**
     * 注册检查菜单项
     *
     * @param checkMenuItem 检查菜单项
     * @param menuItemName  菜单项名称
     * @param userData      用户数据，用来存放必要的数据，比如按钮菜单项名称
     * @param listener      监听器
     */
    public void registerMenuItem(CheckMenuItem checkMenuItem, String menuItemName, Object userData, ChangeListener<Boolean> listener) {
        getMenuItems().put(menuItemName, checkMenuItem);
        checkMenuItem.setUserData(userData);
        checkMenuItem.selectedProperty().addListener(listener);
    }

    /**
     * 在给定菜单映射中注册具有指定名称、用户数据和事件处理程序的菜单项。
     *
     * @param menu         菜单项映射
     * @param menuItemName 要注册的菜单项的名称
     * @param userData     与菜单项关联的用户数据
     */
    public void registerMenu(Menu menu, String menuItemName, Object userData) {
        getMenuItems().put(menuItemName, menu);
        menu.setUserData(userData);
    }

    /**
     * Registers a menu item in the specified menu with the given name and user data.
     *
     * @param menu         the menu to register the item in
     * @param menuItemName the name of the menu item
     * @param userData     the user data associated with the menu item
     * @param isDisable    whether the menu item is disabled
     */
    public void registerMenu(Menu menu, String menuItemName, Object userData, boolean isDisable) {
        if (!isDisable) {
            registerMenu(menu, menuItemName, userData);
        }
    }

    /**
     * 注册单选菜单项
     *
     * @param radioMenuItem 单选菜单项
     * @param menuItemName  菜单项名称
     * @param userData      用户数据，用来存放必要的数据
     * @param eventHandler  事件处理器
     */
    public void registerRadioMenuItem(Map<String, RadioMenuItem> radioMenuItems, RadioMenuItem radioMenuItem, String menuItemName, Object userData, EventHandler<ActionEvent> eventHandler) {
        radioMenuItems.put(menuItemName, radioMenuItem);
        radioMenuItem.setUserData(userData);
        radioMenuItem.setOnAction(eventHandler);
    }

    /**
     * 初始化菜单项
     *
     * @param menuItems 菜单项集合
     * @param menu      菜单
     */
    protected abstract void initMenuItems(Map<String, MenuItem> menuItems, T menu);

}
