package org.jcnc.jnotepad.ui.component.module.vbox.components;

import org.jcnc.jnotepad.ui.component.module.TextCodeArea;

/**
 * 调试信息显示界面。
 *
 * <p>这个类实现了一个用于显示调试信息的界面，它是TextCodeArea的子类，并在构造函数中将其设置为不可编辑状态。</p>
 *
 * <p>调试信息可以通过调用setText方法将文本添加到界面中。</p>
 *
 * @author cccqyu
 */
public class DebugBox extends TextCodeArea {
    /**
     * 创建DebugBox对象的构造函数。
     */
    public DebugBox() {
        super();
        this.setEditable(false);
    }

    /**
     * 设置调试信息的文本内容。
     *
     * @param text 要显示的调试信息文本
     */
    public void setText(String text) {
        this.appendText(text);
    }
}
