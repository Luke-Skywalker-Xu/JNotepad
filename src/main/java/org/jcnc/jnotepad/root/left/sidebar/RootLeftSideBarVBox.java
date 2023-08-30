package org.jcnc.jnotepad.root.left.sidebar;

import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootLeftSideBarVBox extends AbstractVBox {

    private static final RootLeftSideBarVBox INSTANCE = new RootLeftSideBarVBox();

    private RootLeftSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {

    }


    public static RootLeftSideBarVBox getInstance() {
        return INSTANCE;
    }
}
