package org.jcnc.jnotepad.api.core.views.menu.builder;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * 菜单建造者类
 *
 * <p>
 * 此类用于构建菜单对象，可以添加菜单项、单选菜单项、复选菜单项以及分割线等。
 * </p>
 *
 * @author gewuyou
 */
public class MenuBuilder extends AbstractMenuBuilder<MenuBuilder, Menu> {

    private final Menu menu;


    /**
     * 构造菜单建造者
     *
     * @param label 菜单的标签
     */
    public MenuBuilder(String label) {
        menu = new Menu(label);
    }

    /**
     * 获取子类的建造者实例
     *
     * @return 建造者实例
     */
    @Override
    protected MenuBuilder getBuilder() {
        return this;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    protected Menu getMenu() {
        return menu;
    }

    /**
     * 获取菜单的菜单项列表
     *
     * @return 菜单项列表
     */
    @Override
    protected ObservableList<MenuItem> getItems() {
        return menu.getItems();
    }

}
