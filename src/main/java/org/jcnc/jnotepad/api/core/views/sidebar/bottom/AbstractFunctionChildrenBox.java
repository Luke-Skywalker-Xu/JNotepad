package org.jcnc.jnotepad.api.core.views.sidebar.bottom;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.jcnc.jnotepad.views.root.bottom.function.FunctionBox;

/**
 * 子功能栏抽象类
 *
 * <p>
 * 此抽象类用于构建一个基本的子功能栏，包括功能按钮的初始化和添加到功能栏中。
 * </p>
 *
 * @author gewuyou
 *
 */
public abstract class AbstractFunctionChildrenBox {
    protected final FunctionBox functionBox;

    protected final MenuBar menuBar;

    protected final Label label = new Label(getFunctionName());

    protected Menu menu = new Menu();

    /**
     * 构造子功能栏抽象类
     */
    protected AbstractFunctionChildrenBox() {
        functionBox = FunctionBox.getInstance();
        menuBar = FunctionBox.getMenuBar();
    }

    /**
     * 初始化子功能栏，包括将功能按钮添加到菜单栏中
     */
    public void initialize() {
        menu.setGraphic(label);
        menuBar.getMenus().add(menu);
    }

    /**
     * 获取功能按钮名称
     *
     * @return 功能按钮名称
     */
    protected abstract String getFunctionName();
}
