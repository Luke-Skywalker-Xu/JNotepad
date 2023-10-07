package org.jcnc.jnotepad.controller.event.handler.menuitem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.component.stage.dialog.factory.impl.BasicFileChooserFactory;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.PopUpUtil;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;
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
    static Logger logger = LogUtil.getLogger(RenameFile.class);

    /**
     * 重命名
     */
    public static void rename() {
        // 获取当前标签页
        CenterTab centerTab = CenterTabPaneManager.getInstance().getSelected();
        if (centerTab == null || centerTab.getText().isEmpty()) {
            return;
        }
        // 判断当前是否为关联文件
        if (centerTab.isRelevance()) {
            // 重命名关联文件
            handleRenameRelevanceFile(centerTab);
        }
        // 如果当前不是关联文件则重命名标签页
        else {
            handleRenameTab(centerTab);
        }
    }

    /**
     * 重命名标签页。
     *
     * @param centerTab 标签页组件
     */
    private static void handleRenameTab(CenterTab centerTab) {
        TextField textField = new TextField(centerTab.getText());
        textField.getStyleClass().add("tab-title-editable");
        // 临时记录标签页名称
        String tempName = centerTab.getText();
        // 清空标签页名称
        centerTab.setText("");

        // 监听 Enter 键，完成编辑
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newTabName = textField.getText();
                // 检查是否存在相同名称的标签页
                if (isTabNameExists(newTabName)) {

                    // 显示弹窗并提示用户更换名称
                    showDuplicateNameAlert(newTabName);

                    // 恢复原始名称
                    centerTab.setText(tempName);

                } else {
                    centerTab.setText(newTabName);
                    // 可选：移除 TextField 的图形
                    centerTab.setGraphic(null);
                    // 可选：恢复标签页的关闭按钮
                    centerTab.setClosable(true);
                }
            }
        });

        // 监听失去焦点事件，完成编辑
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            String newTabName = textField.getText();
            // 检查是否存在相同名称的标签页
            if (isTabNameExists(newTabName)) {
                // 恢复原始名称
                centerTab.setText(tempName);

            }
            if (Boolean.FALSE.equals(newValue)) {
                centerTab.setText(newTabName);
                // 可选：移除 TextField 的图形
                centerTab.setGraphic(null);
                // 可选：恢复标签页的关闭按钮
                centerTab.setClosable(true);

            }
        });

        centerTab.setClosable(false);
        // 设置 TextField 作为标签页的图形
        centerTab.setGraphic(textField);
        // 默认获取焦点并选中所有文字
        textField.requestFocus();
        textField.selectAll();
    }

    /**
     * 判断是否存在具有相同名称的标签页。
     *
     * @param newTabName 要检查的新标签页名称
     * @return 如果存在具有相同名称的标签页，则返回 true；否则返回 false
     */
    private static boolean isTabNameExists(String newTabName) {
        CenterTabPane tabPane = CenterTabPane.getInstance();
        return tabPane.getTabs().stream()
                .anyMatch(tab -> tab.getText().equals(newTabName));
    }

    /**
     * 显示警告弹窗，提示用户更换重复的名称。
     */
    private static void showDuplicateNameAlert(String newTabName) {

        PopUpUtil.errorAlert("重命名错误", "\" " + newTabName + "\" 和已有标签页名字重复", "请再次重命名", null, null);

    }

    /**
     * 重命名关联文件。
     *
     * @param centerTab 标签页组件
     */
    private static void handleRenameRelevanceFile(CenterTab centerTab) {
        // 获取原始文件对象
        File file = (File) centerTab.getUserData();

        // 获取应用窗口并绑定
        File newFile = BasicFileChooserFactory.getInstance()
                .createFileChooser(
                        UiResourceBundle.getContent(TextConstants.RENAME),
                        centerTab.getText(),
                        new File(file.getParent()),
                        new FileChooser.ExtensionFilter("All types", "*.*"))
                .showSaveDialog(UiUtil.getAppWindow());
        if (newFile != null) {
            boolean rename = file.renameTo(newFile);
            // 设置文件数据
            centerTab.setUserData(newFile);
            if (rename) {
                centerTab.setText(newFile.getName());
                logger.info("文件重命名成功");
            } else {
                logger.debug("文件重命名失败");
            }
        }
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        rename();
    }
}
