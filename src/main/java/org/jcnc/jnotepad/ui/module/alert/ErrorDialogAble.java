package org.jcnc.jnotepad.ui.module.alert;

/**
 * 错误对话框
 *
 * <p>用于显示错误信息的自定义对话框，包含图标、消息文本和确认按钮。</p>
 *
 * @author luke
 */
class ErrorDialogAble extends AbstractDialog {

    /**
     * 构造一个错误对话框。
     *
     * @param title    对话框中显示的错误消息
     * @param customText 自定义文本
     * @param width      对话框的宽度
     * @param height     对话框的高度
     */
    protected ErrorDialogAble(String title, String customText, double width, double height) {
        super(title, customText, width, height);
    }

    /**
     * 构造一个错误对话框，使用默认大小。
     *
     * @param title    对话框中显示的错误消息
     * @param customText 自定义文本
     */
    public ErrorDialogAble(String title, String customText) {
        super(title, customText);
    }

    /**
     * 获取对话框类型。
     *
     * @return 对话框类型为"错误提示"
     */
    @Override
    public String getAlertType() {
        return "错误提示";
    }

    /**
     * 处理确认按钮的操作，子类可以在这里添加具体的处理逻辑。
     */
    @Override
    public void handleConfirmAction() {
    }
}
