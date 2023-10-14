package org.jcnc.jnotepad.ui.component.stage.dialog.interfaces;

import org.jcnc.jnotepad.ui.component.stage.dialog.AppDialogStage;

/**
 * 对话框按钮点击事件接口
 *
 * @author gewuyou
 */
public interface DialogButtonAction {
    /**
     * 处理按钮的操作。子类必须实现此方法以定义按钮的行为
     *
     * @param appDialogStage 对话框
     * @apiNote
     * @since 2023/9/3 22:53
     */

    void handleAction(AppDialogStage appDialogStage);
}
