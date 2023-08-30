package org.jcnc.jnotepad.root;

import org.jcnc.jnotepad.root.center.main.MainBorderPane;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

public class RootBorderPane extends AbstractBorderPane {

    private static final RootBorderPane INSTANCE = new RootBorderPane();

    private RootBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        setCenterComponent(MainBorderPane.getInstance());
    }

    public static RootBorderPane getInstance() {
        return INSTANCE;
    }
}


