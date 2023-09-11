package org.jcnc.jnotepad.ui.dialog.interfaces;

/**
 * 对话框按钮点击事件接口
 *
 * @author gewuyou
 */
public interface DialogButtonAction {
    /**
     * 处理按钮的操作。子类必须实现此方法以定义按钮的行为
     *
     * @apiNote
     * @since 2023/9/3 22:53
     */

    void handleAction();
}
