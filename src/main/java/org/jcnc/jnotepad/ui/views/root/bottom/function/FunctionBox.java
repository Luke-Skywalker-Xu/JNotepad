package org.jcnc.jnotepad.ui.views.root.bottom.function;

import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * 功能栏
 *
 * @author gewuyou
 */
public class FunctionBox extends HBox {
    private static final FunctionBox INSTANCE = new FunctionBox();

    private static final MenuBar MENU_BAR = new MenuBar();

    static {
        HBox.setHgrow(MENU_BAR, Priority.ALWAYS);
        MENU_BAR.setPadding(new Insets(-3, 0, -3, 35));
    }

    private FunctionBox() {

    }

    public static FunctionBox getInstance() {
        return INSTANCE;
    }

    public static MenuBar getMenuBar() {
        return MENU_BAR;
    }
}
