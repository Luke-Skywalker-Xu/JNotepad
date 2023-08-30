package org.jcnc.jnotepad.root.top;

import javafx.geometry.Insets;
import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootTopSideBarVBox extends AbstractVBox {

    private static final RootTopSideBarVBox INSTANCE = new RootTopSideBarVBox();

    private RootTopSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {
        setPadding(new Insets(10));
        setSpacing(10);
    }

    public static RootTopSideBarVBox getInstance() {
        return INSTANCE;
    }
}
