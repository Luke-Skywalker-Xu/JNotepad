package org.jcnc.jnotepad.ui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import org.jcnc.jnotepad.model.enums.DialogType;
import org.jcnc.jnotepad.ui.dialog.interfaces.DialogButtonAction;
import org.jcnc.jnotepad.util.UiUtil;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * 应用对话框建造者类
 *
 * @author gewuyou
 */
public class AppDialogBuilder {
    private AppDialog appDialog;
    private Image appIcon = UiUtil.getAppIcon();
    private String title;
    private String headerText;
    private String customText;
    private double width = 350;
    private double height = 150;
    private FontIcon icon;

    private DialogButtonAction leftBtnAction = () -> appDialog.close();

    private DialogButtonAction rightBtnAction = () -> appDialog.close();

    private String leftBtnText = "确定";

    private String rightBtnText = "取消";

    private Insets iconCoxPaddingInsets = new Insets(10, 10, 10, 10);
    private Insets hBoxPaddingInsets = new Insets(10, 10, 10, 10);
    private boolean isResizable = false;
    private double hBoxSpacing = 10;
    private Pos vboxPos = Pos.CENTER;
    private Pos hboxPos = Pos.CENTER_RIGHT;
    private Modality modality = Modality.APPLICATION_MODAL;

    /**
     * 设置默认的对话框构造
     *
     * @param type 对话框类型
     * @return org.jcnc.jnotepad.ui.dialog.AppDialogBuilder
     * @apiNote 该方法只会设置默认的对话框配置，标题、图标，头文本和自定义文本需要自行设置
     * @since 2023/9/3 22:24
     */
    public AppDialogBuilder setDialogType(DialogType type) {
        switch (type) {
            case INFO ->
            // 设置默认的对话框配置
            {
                return setTitle("信息").
                        setIcon(UiUtil.getInfoIcon());
            }
            case WARNING ->
            // 设置默认的对话框配置
            {
                return setTitle("警告").
                        setIcon(UiUtil.getWarningIcon());
            }
            case ERROR ->
            // 设置默认的对话框配置
            {
                return setTitle("错误").
                        setIcon(UiUtil.getErrorIcon());
            }
            case QUESTION ->
            // 设置默认的对话框配置
            {
                return setTitle("问题").
                        setIcon(UiUtil.getQuestionIcon());
            }
            case SUCCESS ->
            // 设置默认的对话框配置
            {
                return setTitle("成功").
                        setIcon(UiUtil.getSuccessIcon());
            }
            default -> {
                return this;
            }
        }
    }

    /**
     * 设置应用图标
     */
    public AppDialogBuilder setAppIcon(Image appIcon) {
        this.appIcon = appIcon;
        return this;
    }

    public AppDialog build() {
        appDialog = new AppDialog(this);
        return appDialog;
    }

    /**
     * 设置对话框标题
     */
    public AppDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }


    /**
     * 设置对话框头部文本
     */
    public AppDialogBuilder setHeaderText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    /**
     * 设置自定义文本
     */
    public AppDialogBuilder setCustomText(String customText) {
        this.customText = customText;
        return this;
    }

    /**
     * 设置对话框宽度
     */
    public AppDialogBuilder setWidth(double width) {
        this.width = width;
        return this;
    }

    /**
     * 设置对话框高度
     */
    public AppDialogBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    /**
     * 设置对话框左侧图标
     */
    public AppDialogBuilder setIcon(FontIcon icon) {
        this.icon = icon;
        return this;
    }

    /**
     * 设置左按钮操作
     */
    public AppDialogBuilder setLeftBtnAction(DialogButtonAction leftBtnAction) {
        if (leftBtnAction != null) {
            this.leftBtnAction = leftBtnAction;
        }
        return this;
    }

    /**
     * 设置右按钮操作
     */
    public AppDialogBuilder setRightBtnAction(DialogButtonAction rightBtnAction) {
        if (rightBtnAction != null) {
            this.rightBtnAction = rightBtnAction;
        }
        return this;
    }

    /**
     * 设置左按钮文本
     */
    public AppDialogBuilder setLeftBtnText(String leftBtnText) {
        this.leftBtnText = leftBtnText;
        return this;
    }

    /**
     * 设置右按钮文本
     */
    public AppDialogBuilder setRightBtnText(String rightBtnText) {
        this.rightBtnText = rightBtnText;
        return this;
    }

    /**
     * 设置图标边距
     */
    public AppDialogBuilder setIconCoxPaddingInsets(Insets iconCoxPaddingInsets) {
        this.iconCoxPaddingInsets = iconCoxPaddingInsets;
        return this;
    }

    /**
     * 设置水平盒子边距
     */
    public AppDialogBuilder setHorizontalBoxPaddingInsets(Insets hBoxPaddingInsets) {
        this.hBoxPaddingInsets = hBoxPaddingInsets;
        return this;
    }

    /**
     * 设置是否可调整大小
     */
    public AppDialogBuilder setResizable(boolean resizable) {
        isResizable = resizable;
        return this;
    }

    /**
     * 设置水平盒子间距
     */
    public AppDialogBuilder setHorizontalBoxSpacing(double hBoxSpacing) {
        this.hBoxSpacing = hBoxSpacing;
        return this;
    }

    /**
     * 设置垂直盒子位置
     */
    public AppDialogBuilder setVboxPos(Pos vboxPos) {
        this.vboxPos = vboxPos;
        return this;
    }

    /**
     * 设置水平盒子位置
     */
    public AppDialogBuilder setHorizontalBoxPos(Pos hboxPos) {
        this.hboxPos = hboxPos;
        return this;
    }

    /**
     * 设置模态性
     */
    public AppDialogBuilder setModality(Modality modality) {
        this.modality = modality;
        return this;
    }

    public Image getAppIcon() {
        return appIcon;
    }

    public String getTitle() {
        return title;
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getCustomText() {
        return customText;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public FontIcon getIcon() {
        return icon;
    }

    public DialogButtonAction getLeftBtnAction() {
        return leftBtnAction;
    }

    public DialogButtonAction getRightBtnAction() {
        return rightBtnAction;
    }

    public String getLeftBtnText() {
        return leftBtnText;
    }

    public String getRightBtnText() {
        return rightBtnText;
    }

    public Insets getIconCoxPaddingInsets() {
        return iconCoxPaddingInsets;
    }

    public Insets gethBoxPaddingInsets() {
        return hBoxPaddingInsets;
    }

    public boolean isResizable() {
        return isResizable;
    }

    public double getHBoxSpacing() {
        return hBoxSpacing;
    }

    public Pos getVboxPos() {
        return vboxPos;
    }

    public Pos getHboxPos() {
        return hboxPos;
    }

    public Modality getModality() {
        return modality;
    }
}
