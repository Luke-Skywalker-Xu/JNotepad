package org.jcnc.jnotepad.root.top;

import org.jcnc.jnotepad.root.top.menu.JNotepadMenuBar;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

/*
 *  MainBorderPane区域,用于显示文本框以及文本框周边
 * */
public class RootTopBorderPane extends AbstractBorderPane {

    private static final RootTopBorderPane INSTANCE = new RootTopBorderPane();

    private RootTopBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        setTopComponent(JNotepadMenuBar.getInstance());     //文本框上面
    }

    public static RootTopBorderPane getInstance() {
        return INSTANCE;
    }
}


