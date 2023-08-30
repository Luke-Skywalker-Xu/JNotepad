package org.jcnc.jnotepad.root.left.sidebar;

import javafx.geometry.Insets;
import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootLeftSideBarVBox extends AbstractVBox {

    private static final RootLeftSideBarVBox INSTANCE = new RootLeftSideBarVBox();

    private RootLeftSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {
        setPadding(new Insets(10));
        setSpacing(10);
    }

    public static RootLeftSideBarVBox getInstance() {
        return INSTANCE;
    }
}
