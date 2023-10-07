package org.jcnc.jnotepad.component.module.vbox.components;

import org.jcnc.jnotepad.component.module.TextCodeArea;

/**
 * Debug终端界面。
 *
 *
 * @author cccqyu
 */
public class DebugBox extends TextCodeArea {
    public DebugBox() {
        super();
        this.setEditable(false);
    }

    public void setText(String text) {
        this.appendText(text);
    }
}
