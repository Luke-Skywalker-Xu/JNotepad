package org.jcnc.jnotepad.root.right;

import org.jcnc.jnotepad.ui.module.AbstractVerticalBox;

/**
 * @author 许轲
 */
public class RootRightSideBarVerticalBox extends AbstractVerticalBox {

    private static final RootRightSideBarVerticalBox INSTANCE = new RootRightSideBarVerticalBox();

    private RootRightSideBarVerticalBox() {
        initSidebarVerticalBox();
    }

    private void initSidebarVerticalBox() {

    }

    public static RootRightSideBarVerticalBox getInstance() {
        return INSTANCE;
    }
}
