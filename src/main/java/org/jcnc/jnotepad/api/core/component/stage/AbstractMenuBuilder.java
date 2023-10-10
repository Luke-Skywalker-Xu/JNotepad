package org.jcnc.jnotepad.api.core.component.stage;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

/**
 * 抽象菜单建造者类
 *
 * <p>
 * 该抽象类用于构建菜单，包括菜单项、单选菜单项、复选菜单项、分割线等。
 * 子类应继承此类以实现具体的菜单构建逻辑。
 * </p>
 *
 * @param <B> 建造者类型
 * @param <T> 构建结果类型
 *
 * @author gewuyou
 */
public abstract class AbstractMenuBuilder<B, T> {
    /**
     * 获取子类的建造者实例
     *
     * @return 建造者实例
     */
    protected abstract B getBuilder();

    /**
     * 获取菜单的菜单项列表
     *
     * @return 菜单项列表
     */
    protected abstract ObservableList<MenuItem> getItems();

    /**
     * 添加菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件处理器
     * @return 当前建造者实例
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
     * @param eventHandler 事件处理器
     * @param disable      是否禁用
     * @return 当前建造者实例
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
     * 添加单选菜单项
     *
     * @param label        菜单项名称
     * @param eventHandler 事件处理器
     * @return 当前建造者实例
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
     * @param eventHandler 事件处理器
     * @return 当前建造者实例
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
     * @return 当前建造者实例
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
     * @return 当前建造者实例
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
     * @return 当前建造者实例
     */
    public B addSeparatorMenuItem() {
        getItems().add(new SeparatorMenuItem());
        return getBuilder();
    }

    /**
     * 构建菜单
     *
     * @return 构建的菜单
     */
    public abstract T build();
}
