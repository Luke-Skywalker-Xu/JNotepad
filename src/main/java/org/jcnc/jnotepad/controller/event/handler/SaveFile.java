package org.jcnc.jnotepad.controller.event.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;
import org.slf4j.Logger;

import static org.jcnc.jnotepad.tool.FileUtil.saveTab;

/**
 * 保存文件
 *
 * @author gewuyou
 */
public class SaveFile implements EventHandler<ActionEvent> {
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 保存文件
     *
     * @param actionEvent 事件对象
     * @apiNote
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // 获取当前tab页
        JNotepadTab selectedTab = JNotepadTabPane.getInstance().getSelected();
        if (selectedTab == null) {
            return;
        }
        // 获取当前Tab页的文本编辑区
        LineNumberTextArea textArea = (LineNumberTextArea) selectedTab.getContent();
        // 打开的是非关联文件，则调用另存为api
        if (!textArea.isRelevance()) {
            logger.info("当前保存文件为非关联打开文件，调用另存为方法");
            saveTab(this.getClass());
        } else {
            logger.info("当前保存文件为关联打开文件，调用自动保存方法");
            // 调用tab保存
            selectedTab.save();
        }
    }
}
