package org.jcnc.jnotepad.ui.root.top.tools;

import org.jcnc.jnotepad.ui.module.AbstractBorderPane;
import org.jcnc.jnotepad.ui.root.top.menu.JNotepadMenuBar;

public class ToolBorderPane extends AbstractBorderPane {

    private static final ToolBorderPane ToolBorderPane = new ToolBorderPane();

    public ToolBorderPane() {

        setRightComponent(ToolHBox.getInstance());

    }

    public static ToolBorderPane getInstance() {
        return ToolBorderPane;
    }
}
