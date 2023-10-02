package org.jcnc.jnotepad.common.util;

import org.jcnc.jnotepad.model.enums.DialogType;
import org.jcnc.jnotepad.ui.dialog.AppDialogBuilder;
import org.jcnc.jnotepad.ui.dialog.interfaces.DialogButtonAction;

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
            DialogButtonAction leftBtnAction, DialogButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(DialogType.ERROR)
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
            DialogButtonAction leftBtnAction, DialogButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(DialogType.INFO)
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
            DialogButtonAction leftBtnAction, DialogButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(DialogType.WARNING)
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
            DialogButtonAction leftBtnAction, DialogButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(DialogType.QUESTION)
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
            DialogButtonAction leftBtnAction, DialogButtonAction rightBtnAction, String leftBtnText, String rightBtnText) {
        getCustomDialog()
                .setDialogType(DialogType.QUESTION)
                .setTitle(title)
                .setHeaderText(headerText)
                .setCustomText(message)
                .setLeftBtnAction(leftBtnAction)
                .setRightBtnAction(rightBtnAction)
                .setLeftBtnText(leftBtnText)
                .setRightBtnText(rightBtnText)
                .build().showAndWait();
    }

    public static void successAlert(
            String title, String headerText, String message,
            DialogButtonAction leftBtnAction, DialogButtonAction rightBtnAction) {
        getCustomDialog()
                .setDialogType(DialogType.SUCCESS)
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
    public static AppDialogBuilder getCustomDialog() {
        return new AppDialogBuilder();
    }
}
