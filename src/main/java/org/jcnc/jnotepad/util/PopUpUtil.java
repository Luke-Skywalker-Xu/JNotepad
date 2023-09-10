package org.jcnc.jnotepad.util;

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
     * 设置错误弹窗
     *
     * @param title          弹窗标题
     * @param headerText     头文本
     * @param message        消息文本
     * @param leftBtnAction  左侧按钮点击事件
     * @param rightBtnAction 右侧按钮点击事件
     * @apiNote
     * @since 2023/9/3 11:54
     */
    public static void errorAlert(
            String title, String headerText, String message,
            AppDialog.ButtonAction leftBtnAction, AppDialog.ButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(AppDialog.DialogType.ERROR)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .build().showAndWait();
    }

    /**
     * 设置信息弹窗
     *
     * @param title          弹窗标题
     * @param headerText     头文本
     * @param message        消息文本
     * @param leftBtnAction  左侧按钮点击事件
     * @param rightBtnAction 右侧按钮点击事件
     * @apiNote
     * @since 2023/9/3 11:54
     */
    public static void infoAlert(
            String title, String headerText, String message,
            AppDialog.ButtonAction leftBtnAction, AppDialog.ButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(AppDialog.DialogType.INFO)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .build().showAndWait();
    }

    /**
     * 设置警告弹窗
     *
     * @param title          弹窗标题
     * @param headerText     头文本
     * @param message        消息文本
     * @param leftBtnAction  左侧按钮点击事件
     * @param rightBtnAction 右侧按钮点击事件
     * @apiNote
     * @since 2023/9/3 11:54
     */
    public static void warningAlert(
            String title, String headerText, String message,
            AppDialog.ButtonAction leftBtnAction, AppDialog.ButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(AppDialog.DialogType.WARNING)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .build().showAndWait();
    }

    /**
     * 设置疑问弹窗
     *
     * @param title          弹窗标题
     * @param headerText     头文本
     * @param message        消息文本
     * @param leftBtnAction  左侧按钮点击事件
     * @param rightBtnAction 右侧按钮点击事件
     * @apiNote
     * @since 2023/9/3 11:54
     */
    public static void questionAlert(
            String title, String headerText, String message,
            AppDialog.ButtonAction leftBtnAction, AppDialog.ButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(AppDialog.DialogType.QUESTION)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .build().showAndWait();
    }

    public static void successAlert(
            String title, String headerText, String message,
            AppDialog.ButtonAction leftBtnAction, AppDialog.ButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(AppDialog.DialogType.SUCCESS)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .build().showAndWait();

    }

    /**
     * 获取自定义弹窗
     *
     * @apiNote 使用此方法会返回原始的应用对话框建造者类，以实现自定义弹窗
     * @since 2023/9/3 11:54
     */
    public static AppDialog.AppDialogBuilder getCustomDialog() {
        return new AppDialog.AppDialogBuilder();
    }
}
