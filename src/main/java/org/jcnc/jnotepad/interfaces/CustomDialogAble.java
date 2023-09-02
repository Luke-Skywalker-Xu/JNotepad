package org.jcnc.jnotepad.interfaces;

import javafx.scene.image.Image;

/**
 * 自定义提示框的接口。
 *
 * <p>此接口定义了自定义提示框的基本行为，包括设置图标、处理确认和取消按钮操作以及获取提示框类型。</p>
 *
 * @author luke
 */
public interface CustomDialogAble {

    /**
     * 设置提示框的图标。
     *
     * @param iconImage 图标图像
     */
    void setIconImage(Image iconImage);

    /**
     * 处理确认按钮的操作。子类必须实现此方法以定义确认按钮的行为。
     */
    void handleConfirmAction();

    /**
     * 获取提示框类型。
     *
     * @return 提示框类型的描述
     */
    String getAlertType();

    /**
     * 处理取消按钮的操作。默认情况下，此方法为空，子类可以选择性地实现它。
     */
    default void handleCancelAction() {
    }
}
