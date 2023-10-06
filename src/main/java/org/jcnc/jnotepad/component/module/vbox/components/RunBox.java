package org.jcnc.jnotepad.component.module.vbox.components;

import org.jcnc.jnotepad.component.module.TextCodeArea;

/**
 * Run终端界面。
 *
 *
 * @author cccqyu
 */
public class RunBox extends TextCodeArea {


    public RunBox() {
        super();
        this.setEditable(false);
    }

    public void setText(String text) {
        this.appendText(text);
    }

}
