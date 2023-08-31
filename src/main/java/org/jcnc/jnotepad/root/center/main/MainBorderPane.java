package org.jcnc.jnotepad.root.center.main;

import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

/*
 *  MainBorderPane区域,用于显示文本框以及文本框周边
 * */
public class MainBorderPane extends AbstractBorderPane {

    private static final MainBorderPane INSTANCE = new MainBorderPane();

    private MainBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {

        setCenterComponent(JNotepadTabPane.getInstance());   //文本框

    }

    public static MainBorderPane getInstance() {
        return INSTANCE;
    }
}


