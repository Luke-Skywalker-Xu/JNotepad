package org.jcnc.jnotepad.tool;

import org.jcnc.jnotepad.ui.dialog.AppDialog;

/**
 * 弹窗工具类
 *
 * @author gewuyou
 */
public class PopUpUtil {

    private PopUpUtil() {
    }

    /**
     * 获取错误弹窗
     *
     * @param title          弹窗标题
     * @param headerText     头文本
     * @param message        消息文本
     * @param leftBtnAction  左侧按钮点击事件
     * @param rightBtnAction 右侧按钮点击事件
     * @apiNote
     * @since 2023/9/3 11:54
     */
    public static void errorAlert(String title, String headerText, String message, AppDialog.ButtonAction leftBtnAction, AppDialog.ButtonAction rightBtnAction) {
        AppDialog.AppDialogBuilder builder = new AppDialog.AppDialogBuilder();
        builder
                .setDialogType(AppDialog.DialogType.ERROR)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .build().showAndWait();
    }
}
