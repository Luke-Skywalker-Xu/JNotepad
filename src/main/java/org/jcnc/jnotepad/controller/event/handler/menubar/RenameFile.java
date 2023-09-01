package org.jcnc.jnotepad.controller.event.handler.menubar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.root.center.main.center.tab.MainTab;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.UiUtil;
import org.jcnc.jnotepad.ui.dialog.factory.impl.TextFileChooserFactory;
import org.slf4j.Logger;

import java.io.File;

/**
 * 重命名文件事件处理器。
 * <p>
 * 当用户选择重命名文件时，如果当前标签页关联文件，则重命名关联文件；
 * 否则，重命名标签页。
 *
 * @author gewuyou
 */
public class RenameFile implements EventHandler<ActionEvent> {
    Logger logger = LogUtil.getLogger(this.getClass());

    @Override
    public void handle(ActionEvent actionEvent) {
        // 获取当前标签页
        MainTab jnotepadtab = UiUtil.getJnotepadtab();
        if (jnotepadtab == null || jnotepadtab.getText().isEmpty()) {
            return;
        }
        // 判断当前是否为关联文件
        if (jnotepadtab.isRelevance()) {
            // 重命名关联文件
            handleRenameRelevanceFile(jnotepadtab);
        }
        // 如果当前不是关联文件则重命名标签页
        else {
            handleRenameTab(jnotepadtab);
        }
    }

    /**
     * 重命名标签页。
     *
     * @param jnotepadtab 标签页组件
     */
    private void handleRenameTab(MainTab jnotepadtab) {
        TextField textField = new TextField(jnotepadtab.getText());
        textField.getStyleClass().add("tab-title-editable");
        // 清空标签页名称
        jnotepadtab.setText("");
        // 监听 Enter 键，完成编辑
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                jnotepadtab.setText(textField.getText());
                // 可选：移除 TextField 的图形
                jnotepadtab.setGraphic(null);
                // 可选：恢复标签页的关闭按钮
                jnotepadtab.setClosable(true);
            }
        });
        // 监听失去焦点事件，完成编辑
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                jnotepadtab.setText(textField.getText());
                // 可选：移除 TextField 的图形
                jnotepadtab.setGraphic(null);
                // 可选：恢复标签页的关闭按钮
                jnotepadtab.setClosable(true);
            }
        });
        jnotepadtab.setClosable(false);
        // 设置 TextField 作为标签页的图形
        jnotepadtab.setGraphic(textField);
        // 默认获取焦点并选中所有文字
        textField.requestFocus();
        textField.selectAll();
    }

    /**
     * 重命名关联文件。
     *
     * @param jnotepadtab 标签页组件
     */
    private void handleRenameRelevanceFile(MainTab jnotepadtab) {
        // 获取原始文件对象
        File file = (File) jnotepadtab.getUserData();
        // 获取应用窗口并绑定
        File newFile = TextFileChooserFactory.getInstance()
                .createFileChooser(
                        UiResourceBundle.getContent(TextConstants.RENAME),
                        jnotepadtab.getText(),
                        new File(file.getParent()),
                        new FileChooser.ExtensionFilter("All types", "*.*"))
                .showSaveDialog(UiUtil.getAppWindow());
        if (newFile != null) {
            boolean rename = file.renameTo(newFile);
            // 设置文件数据
            jnotepadtab.setUserData(newFile);
            if (rename) {
                jnotepadtab.setText(newFile.getName());
                logger.info("文件重命名成功");
            } else {
                logger.debug("文件重命名失败");
            }
        }
    }
}
