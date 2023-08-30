package org.jcnc.jnotepad.root.bottom;

import javafx.geometry.Insets;
import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootBottomSideBarVBox extends AbstractVBox {

    private static final RootBottomSideBarVBox INSTANCE = new RootBottomSideBarVBox();

    private RootBottomSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {
        setPadding(new Insets(10));
        setSpacing(10);
    }

    public static RootBottomSideBarVBox getInstance() {
        return INSTANCE;
    }
}
