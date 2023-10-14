package org.jcnc.jnotepad.api.core.views.menu;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.app.utils.LogUtil;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象菜单类
 *
 * @author gewuyou
 */
public abstract class AbstractMenu<T> {
    protected Logger logger = LogUtil.getLogger(this.getClass());
    UserConfigController userConfigController = UserConfigController.getInstance();

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
     * 获取菜单项
     *
     * @return 菜单项集合
     */
    protected abstract ObservableList<MenuItem> getItems();


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
     */
    protected void initMenuItems(Map<String, MenuItem> menuItems) {
        logger.info("初始化菜单项!");
        Map<String, MenuItem> menuItemMap = new HashMap<>(16);
        menuItems.forEach((key, value) -> {
            UiResourceBundle.bindStringProperty(value.textProperty(), key);
            menuItemMap.put((String) value.getUserData(), value);
            getItems().add(value);
        });
        userConfigController.getMenuItems().add(menuItemMap);
        userConfigController.initShortcutKeys(menuItemMap);
    }

}
