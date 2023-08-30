package org.jcnc.jnotepad.root.top;

import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootTopSideBarVBox extends AbstractVBox {

    private static final RootTopSideBarVBox INSTANCE = new RootTopSideBarVBox();

    private RootTopSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {

    }

    public static RootTopSideBarVBox getInstance() {
        return INSTANCE;
    }
}
