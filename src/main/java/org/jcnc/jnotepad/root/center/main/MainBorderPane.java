package org.jcnc.jnotepad.root.center.main;

import org.jcnc.jnotepad.root.center.main.bottom.status.JNotepadStatusBox;
import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane;
import org.jcnc.jnotepad.root.center.main.top.RootTopVBox;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

public class MainBorderPane extends AbstractBorderPane {

    private static final MainBorderPane INSTANCE = new MainBorderPane();

    private MainBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        this.setBottomComponent(JNotepadStatusBox.getInstance());

        this.setCenter(JNotepadTabPane.getInstance());

        this.setTopComponent(RootTopVBox.getInstance());
    }

    public static MainBorderPane getInstance() {
        return INSTANCE;
    }
}


