package org.jcnc.jnotepad.plugin.api.core.views.sidebar.bottom;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.jcnc.jnotepad.views.root.bottom.function.FunctionBox;

/**
 * 子功能栏抽象类(用于构建一个基本的子功能栏)
 *
 * @author gewuyou
 */
public abstract class AbstractFunctionChildrenBox {
    protected final FunctionBox functionBox;

    protected final MenuBar menuBar;

    protected final Label label = new Label(getFunctionName());

    protected Menu menu = new Menu();

    protected AbstractFunctionChildrenBox() {
        functionBox = FunctionBox.getInstance();
        menuBar = FunctionBox.getMenuBar();
    }

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
