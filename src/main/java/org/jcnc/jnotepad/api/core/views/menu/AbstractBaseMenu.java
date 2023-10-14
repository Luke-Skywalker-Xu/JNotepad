package org.jcnc.jnotepad.api.core.views.menu;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.ui.views.root.top.menubar.TopMenuBar;

/**
 * 抽象基础菜单类
 *
 * <p>
 * 此抽象类用于创建基础菜单，包括菜单项的注册和初始化。
 * </p>
 *
 * @author gewuyou
 */
public abstract class AbstractBaseMenu extends AbstractMenu<Menu> {
    protected final TopMenuBar topMenuBar = TopMenuBar.getInstance();

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    public abstract String getMenuName();

    /**
     * 获取菜单项
     *
     * @return 菜单项集合
     */
    @Override
    protected ObservableList<MenuItem> getItems() {
        return getMenu().getItems();
    }

    /**
     * 初始化菜单栏
     */
    @Override
    public void initMenu() {
        registerMenu();
        logger.info("初始化菜单!");
        Menu menu = getMenu();
        // 菜单名称国际化
        UiResourceBundle.bindStringProperty(menu.textProperty(), getMenuName());
        // 初始化菜单项
        initMenuItems(getMenuItems());
    }
}
