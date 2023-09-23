package org.jcnc.jnotepad.controller.event.handler.menubar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.AppConstants;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.ui.module.LineNumberTextArea;
import org.jcnc.jnotepad.views.root.bottom.status.BottomStatusBox;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;

import java.util.Comparator;
import java.util.List;

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
        // 设定初始索引
        int index = 1;
        StringBuilder tabTitle = new StringBuilder();
        // 获取当前默认创建标签页集合
        List<Tab> tabs = CenterTabPane.getInstance()
                .getTabs()
                .stream()
                // 排除不属于默认创建的标签页
                .filter(tab -> AppConstants.TABNAME_PATTERN.matcher(tab.getText()).matches())
                // 对默认创建的标签页进行排序
                .sorted(Comparator.comparing(Tab::getText))
                // 转为List集合
                .toList();
        // 构建初始标签页名称
        tabTitle.append(UiResourceBundle.getContent(TextConstants.NEW_FILE)).append(index);
        for (Tab tab : tabs) {
            if (tab.getText().contentEquals(tabTitle)) {
                tabTitle.setLength(0);
                tabTitle.append(UiResourceBundle.getContent(TextConstants.NEW_FILE)).append(++index);
            } else {
                break;
            }
        }
        // 创建标签页
        CenterTab centerTab = new CenterTab(
                tabTitle.toString(),
                textArea);
        // 设置当前标签页与本地文件无关联
        centerTab.setRelevance(false);
        // 将Tab页添加到TabPane中
        CenterTabPane.getInstance().addNewTab(centerTab);
        // 更新编码信息
        BottomStatusBox.getInstance().updateEncodingLabel();
    }
}
