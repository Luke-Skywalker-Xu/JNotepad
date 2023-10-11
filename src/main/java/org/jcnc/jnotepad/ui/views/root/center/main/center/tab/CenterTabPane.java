package org.jcnc.jnotepad.ui.views.root.center.main.center.tab;

import javafx.scene.control.TabPane;

/**
 * 标签页布局组件封装。
 *
 * @author songdragon
 */
public class CenterTabPane extends TabPane {
    private static final CenterTabPane INSTANCE = new CenterTabPane();

    private CenterTabPane() {

    }

    public static CenterTabPane getInstance() {
        return INSTANCE;
    }

}
