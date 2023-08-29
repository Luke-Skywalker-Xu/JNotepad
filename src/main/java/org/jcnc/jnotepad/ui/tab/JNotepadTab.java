package org.jcnc.jnotepad.ui.tab;

import javafx.scene.control.Tab;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.ui.LineNumberTextArea;

import java.nio.charset.Charset;

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
    /**
     * 是否与本地文件关联
     */
    private boolean isRelevance = false;

    private final LineNumberTextArea lineNumberTextArea;
    private Charset charset = Charset.defaultCharset();

    public boolean isRelevance() {
        return isRelevance;
    }

    public void setRelevance(boolean relevance) {
        isRelevance = relevance;
    }

    public JNotepadTab(String tabTitle) {
        this(tabTitle, new LineNumberTextArea());
    }

    public JNotepadTab(String tabTitle, LineNumberTextArea textArea) {
        this(tabTitle, textArea, Charset.defaultCharset());
    }

    public JNotepadTab(String tabTitle, LineNumberTextArea textArea, Charset charset) {
        super(tabTitle);
        lineNumberTextArea = textArea;
        this.setContent(lineNumberTextArea);
        setAutoLine(AppConfigController.getInstance().getAutoLineConfig());
        this.charset = charset;
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

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void save() {
        this.lineNumberTextArea.save();
    }
}
