package org.jcnc.jnotepad.tool;

import org.jcnc.jnotepad.ui.dialog.alert.AlertDialog;
import org.jcnc.jnotepad.ui.dialog.alert.AlertDialogButtonAction;

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
     * @param title      弹窗标题
     * @param headerText 头文本
     * @param message    消息文本
     * @param action     按钮的事件类
     * @apiNote
     * @since 2023/9/3 11:54
     */
    public static void errorAlert(String title, String headerText, String message, AlertDialogButtonAction action) {
        new AlertDialog(title, headerText, message, AlertDialog.DialogType.ERROR, action).showAndWait();
    }
}
