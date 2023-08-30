package org.jcnc.jnotepad.root.center.main;

import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane;
import org.jcnc.jnotepad.root.center.main.top.MainTopVBox;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

public class MainBorderPane extends AbstractBorderPane {

    private static final MainBorderPane INSTANCE = new MainBorderPane();

    private MainBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        //setBottomComponent(JNotepadStatusBox.getInstance());

        setCenter(JNotepadTabPane.getInstance());

        setTopComponent(MainTopVBox.getInstance());
    }

    public static MainBorderPane getInstance() {
        return INSTANCE;
    }
}


