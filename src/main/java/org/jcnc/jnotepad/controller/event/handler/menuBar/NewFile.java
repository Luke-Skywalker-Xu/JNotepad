package org.jcnc.jnotepad.controller.event.handler.menuBar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.app.i18n.UIResourceBundle;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.tool.UiUtil;
import org.jcnc.jnotepad.ui.module.LineNumberTextArea;
import org.jcnc.jnotepad.ui.root.center.tab.JNotepadTab;
import org.jcnc.jnotepad.view.manager.ViewManager;


/**
 * 新建文件事件的事件处理程序。
 * <p>
 * 当用户选择新建文件时候,将创建一个新的文本编辑区，并在Tab页中显示。
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

    public void addNewFileTab() {
        // 创建一个新的文本编辑区
        LineNumberTextArea textArea = new LineNumberTextArea();
        // TODO: refactor：统一TextArea新建、绑定监听器入口
        ViewManager viewManager = UiUtil.getViewManager();
        // 创建标签页
        JNotepadTab jNotepadTab = new JNotepadTab(
                UIResourceBundle.getContent(TextConstants.NEW_FILE)
                        + viewManager.selfIncreaseAndGetTabIndex(),
                textArea);
        // 设置当前标签页与本地文件无关联
        jNotepadTab.setRelevance(false);
        // 将Tab页添加到TabPane中
        UiUtil.getJnotepadTabPane().addNewTab(jNotepadTab);
        // 更新编码信息
        UiUtil.getStatusBox().updateEncodingLabel();
    }
}