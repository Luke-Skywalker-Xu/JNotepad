package org.jcnc.jnotepad.root.center.main;

import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane;
import org.jcnc.jnotepad.ui.module.AbstractBorderPane;

/**
 * MainBorderPane区域,用于显示文本框以及文本框周边
 *
 * @author 许轲
 */
public class MainBorderPane extends AbstractBorderPane {

    private static final MainBorderPane INSTANCE = new MainBorderPane();

    private MainBorderPane() {
        initRootBorderPane();
    }

    private void initRootBorderPane() {
        //文本框
        setCenterComponent(JNotepadTabPane.getInstance());

    }

    public static MainBorderPane getInstance() {
        return INSTANCE;
    }
}


