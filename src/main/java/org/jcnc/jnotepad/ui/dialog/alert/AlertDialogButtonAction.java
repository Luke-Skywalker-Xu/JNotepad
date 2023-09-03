package org.jcnc.jnotepad.ui.dialog.alert;

/**
 * 对话框按钮事件类
 *
 * @author gewuyou
 */
public abstract class AlertDialogButtonAction {
    /**
     * 处理确认按钮的操作。子类必须实现此方法以定义确认按钮的行为。
     */
    protected abstract void handleConfirmAction();

    /**
     * 处理取消按钮的操作。默认情况下，此方法为空，子类可以选择性地实现它。
     */
    protected abstract void handleCancelAction();
}
