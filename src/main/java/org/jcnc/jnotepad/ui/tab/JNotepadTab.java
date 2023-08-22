package org.jcnc.jnotepad.ui.tab;

import javafx.scene.control.Tab;
import org.jcnc.jnotepad.app.config.GlobalConfig;
import org.jcnc.jnotepad.ui.LineNumberTextArea;

/**
 * 封装标签页组件，增加属于标签页的属性，例如：自动换行开关。
 * 每个Tab关联一个LineNumberTextArea。
 *
 * @author songdragon
 */
public class JNotepadTab extends Tab {

    /**
     * 默认关闭自动换行
     */
    private boolean autoLine = false;
    private final LineNumberTextArea lineNumberTextArea;

    public JNotepadTab(String tabTitle) {
        this(tabTitle, new LineNumberTextArea());
    }

    public JNotepadTab(String tabTitle, LineNumberTextArea textArea) {
        super(tabTitle);
        lineNumberTextArea = textArea;
        this.setContent(lineNumberTextArea);
        setAutoLine(GlobalConfig.getConfig().getAutoLineConfig());
    }

    public boolean isAutoLine() {
        return autoLine;
    }

    public void setAutoLine(boolean autoLine) {
        this.autoLine = autoLine;
        lineNumberTextArea.getMainTextArea().setWrapText(autoLine);
    }

    public LineNumberTextArea getLineNumberTextArea() {
        return lineNumberTextArea;
    }
}
