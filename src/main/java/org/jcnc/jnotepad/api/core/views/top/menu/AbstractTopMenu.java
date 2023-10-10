package org.jcnc.jnotepad.api.core.views.top.menu;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.views.root.top.menubar.TopMenuBar;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 抽象顶部菜单类
 *
 * <p>
 * 此抽象类用于创建顶部菜单，包括菜单项的注册和初始化。
 * </p>
 *
 * @author gewuyou
 */
public abstract class AbstractTopMenu {
    protected final TopMenuBar topMenuBar = TopMenuBar.getInstance();

    protected Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    public abstract String getMenuName();

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    public abstract Menu getMenu();

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    public abstract Map<String, MenuItem> getMenuItems();

    /**
     * 注册顶部菜单
     */
    protected abstract void registerTopMenu();

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
     * 初始化菜单栏
     */
    public void initMenu() {
        registerTopMenu();
        logger.info("初始化菜单!");
        Menu menu = getMenu();
        // 菜单名称国际化
        UiResourceBundle.bindStringProperty(menu.textProperty(), getMenuName());
        // 初始化菜单项
        initMenuItems(getMenuItems(), menu);
    }

    /**
     * 初始化菜单项
     *
     * @param menuItems 菜单项集合
     * @param menu      菜单
     */
    private void initMenuItems(Map<String, MenuItem> menuItems, Menu menu) {
        logger.info("初始化菜单项!");
        var itemMap = topMenuBar.getAllItemMap();
        menuItems.forEach((key, value) -> {
            UiResourceBundle.bindStringProperty(value.textProperty(), key);
            itemMap.put((String) value.getUserData(), value);
            menu.getItems().add(value);
        });
    }
}
