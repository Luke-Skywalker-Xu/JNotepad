package org.jcnc.jnotepad.controller.event.handler.menubar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.root.center.main.bottom.status.JNotepadStatusBox;
import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTab;
import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane;
import org.jcnc.jnotepad.ui.module.LineNumberTextArea;
import org.jcnc.jnotepad.view.manager.ViewManager;

/**
 * 新建文件事件的事件处理程序。
 *
 * <p>当用户选择新建文件时，将创建一个新的文本编辑区，并在Tab页中显示。</p>
 *
 * @author 许轲
 */
public class NewFile implements EventHandler<ActionEvent> {

    /**
     * 处理新建文件事件。
     *
     * @param event 事件对象
     */
    @Override
    public void handle(ActionEvent event) {
        addNewFileTab();
    }

    /**
     * 添加新的文件标签页。
     */
    public void addNewFileTab() {
        // 创建一个新的文本编辑区
        LineNumberTextArea textArea = new LineNumberTextArea();
        // TODO: refactor：统一TextArea新建、绑定监听器入口
        ViewManager viewManager = ViewManager.getInstance();
        // 创建标签页
        JNotepadTab JNotepadTab = new JNotepadTab(
                UiResourceBundle.getContent(TextConstants.NEW_FILE)
                        + viewManager.selfIncreaseAndGetTabIndex(),
                textArea);
        // 设置当前标签页与本地文件无关联
        JNotepadTab.setRelevance(false);
        // 将Tab页添加到TabPane中
        JNotepadTabPane.getInstance().addNewTab(JNotepadTab);
        // 更新编码信息
        JNotepadStatusBox.getInstance().updateEncodingLabel();
    }
}
