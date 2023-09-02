package org.jcnc.jnotepad.ui.module.alert;

/**
 * 信息提示对话框类，继承自AbstractCustomAlert，用于显示信息提示。
 *
 * @author luke
 */
public class InformationDialogAble extends AbstractDialog {

    /**
     * 构造一个信息提示对话框。
     *
     * @param title    提示信息
     * @param customText 自定义文本
     * @param width      对话框宽度
     * @param height     对话框高度
     */
    protected InformationDialogAble(String title, String customText, double width, double height) {
        super(title, customText, width, height);
    }

    /**
     * 构造一个信息提示对话框。
     *
     * @param title 提示信息
     * @param number  编号
     */
    public InformationDialogAble(String title, String number) {
        super(title, number);
    }

    /**
     * 获取对话框类型。
     *
     * @return 对话框类型字符串，此处为"信息提示"
     */
    @Override
    public String getAlertType() {
        return "信息提示";
    }

    /**
     * 处理确认操作的方法。
     */
    @Override
    public void handleConfirmAction() {
    }
}
