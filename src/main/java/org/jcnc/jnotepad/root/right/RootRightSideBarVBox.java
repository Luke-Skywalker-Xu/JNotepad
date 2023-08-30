package org.jcnc.jnotepad.root.right;

import javafx.geometry.Insets;
import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootRightSideBarVBox extends AbstractVBox {

    private static final RootRightSideBarVBox INSTANCE = new RootRightSideBarVBox();

    private RootRightSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {
        setPadding(new Insets(10));
        setSpacing(10);
    }

    public static RootRightSideBarVBox getInstance() {
        return INSTANCE;
    }
}
