package org.jcnc.jnotepad.root.bottom;

import org.jcnc.jnotepad.root.center.main.bottom.status.JNotepadStatusBox;
import org.jcnc.jnotepad.ui.module.AbstractVBox;

public class RootBottomSideBarVBox extends AbstractVBox {

    private static final RootBottomSideBarVBox INSTANCE = new RootBottomSideBarVBox();

    private RootBottomSideBarVBox() {
        initSidebarVBox();
    }

    private void initSidebarVBox() {
        getChildren().addAll(JNotepadStatusBox.getInstance());
    }

    public static RootBottomSideBarVBox getInstance() {
        return INSTANCE;
    }
}
