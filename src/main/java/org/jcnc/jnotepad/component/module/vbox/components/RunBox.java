package org.jcnc.jnotepad.component.module.vbox.components;

import org.jcnc.jnotepad.component.module.TextCodeArea;

/**
 * 运行信息显示界面。
 *
 * <p>这个类实现了一个用于显示运行信息的界面，它是TextCodeArea的子类，并在构造函数中将其设置为不可编辑状态。</p>
 *
 * <p>运行信息可以通过调用setText方法将文本添加到界面中。</p>
 *
 * @author cccqyu
 */
public class RunBox extends TextCodeArea {
    /**
     * 创建RunBox对象的构造函数。
     */
    public RunBox() {
        super();
        this.setEditable(false);
    }

    /**
     * 设置运行信息的文本内容。
     *
     * @param text 要显示的运行信息文本
     */
    public void setText(String text) {
        this.appendText(text);
    }
}
