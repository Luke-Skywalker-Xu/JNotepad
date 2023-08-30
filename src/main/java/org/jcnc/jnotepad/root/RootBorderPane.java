package org.jcnc.jnotepad.root;

import org.jcnc.jnotepad.root.bottom.RootBottomSideBarVBox;
import org.jcnc.jnotepad.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.root.left.sidebar.RootLeftSideBarVBox;
import org.jcnc.jnotepad.root.right.RootRightSideBarVBox;
import org.jcnc.jnotepad.root.top.RootTopSideBarVBox;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

public class RootBorderPane extends AbstractBorderPane {

    private static final RootBorderPane INSTANCE = new RootBorderPane();

    private RootBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        setCenterComponent(MainBorderPane.getInstance());
        setLeftComponent(RootLeftSideBarVBox.getInstance());
        setRightComponent(RootRightSideBarVBox.getInstance());
        setTopComponent(RootTopSideBarVBox.getInstance());
        setBottomComponent(RootBottomSideBarVBox.getInstance());
    }

    public static RootBorderPane getInstance() {
        return INSTANCE;
    }
}


