package org.jcnc.jnotepad.root.right;

import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootRightSideBarVBox extends AbstractVBox {

    private static final RootRightSideBarVBox INSTANCE = new RootRightSideBarVBox();

    private RootRightSideBarVBox() {
        initSidebarVBox();
    }

    public static RootRightSideBarVBox getInstance() {
        return INSTANCE;
    }

    private void initSidebarVBox() {

    }
}
