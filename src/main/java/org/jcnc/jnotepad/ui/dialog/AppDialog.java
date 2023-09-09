package org.jcnc.jnotepad.ui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jcnc.jnotepad.util.UiUtil;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * 应用对话框
 *
 * <p>该类用于创建自定义的提示框窗口，包括图标、消息文本和确认、取消按钮。</p>
 *
 * @author luke gewuyou
 */
public class AppDialog extends Stage {
    /**
     * 构造一个自定义提示框
     *
     * @param builder 提示框构建器
     */
    private AppDialog(
            AppDialogBuilder builder) {
        // 设置窗口图标
        this.getIcons().add(builder.appIcon);
        setTitle(builder.title);
        setResizable(builder.isResizable);
        initModality(builder.modality);

        BorderPane borderPane = createLayout(builder);
        Scene scene = new Scene(borderPane, builder.width, builder.height);

        setScene(scene);
    }

    /**
     * 创建提示框的布局。
     *
     * @return BorderPane 布局容器
     */
    private BorderPane createLayout(
            AppDialogBuilder builder) {
        BorderPane borderPane = new BorderPane();
        HBox iconBox = new HBox(builder.icon);
        iconBox.setPadding(builder.iconCoxPaddingInsets);
        VBox vbox = new VBox(builder.hBoxSpacing);
        vbox.setAlignment(builder.vboxPos);

        Label label = new Label(builder.headerText);

        // 自定义文本
        Label customTextLabel = new Label(builder.customText);

        Button confirmButton = createButton(builder.leftBtnText, builder.leftBtnAction::handleAction);
        Button cancelButton = createButton(builder.rightBtnText, builder.rightBtnAction::handleAction);

        HBox hBox = new HBox(builder.hBoxSpacing, confirmButton, cancelButton);
        hBox.setAlignment(builder.hboxPos);
        hBox.setPadding(builder.hBoxPaddingInsets);
        vbox.getChildren().addAll(label, customTextLabel, hBox);

        borderPane.setLeft(iconBox);
        borderPane.setCenter(vbox);
        borderPane.setBottom(hBox);

        return borderPane;
    }

    /**
     * 创建按钮。
     *
     * @param text   按钮文本
     * @param action 按钮点击时的操作
     * @return Button 按钮控件
     */
    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    public enum DialogType {
        /**
         * 信息
         */
        INFO,
        /**
         * 警告
         */
        WARNING,
        /**
         * 错误
         */
        ERROR,
        /**
         * 疑问
         */
        QUESTION
    }


    public interface ButtonAction {
        /**
         * 处理按钮的操作。子类必须实现此方法以定义按钮的行为
         *
         * @apiNote
         * @since 2023/9/3 22:53
         */

        void handleAction();
    }

    public static class AppDialogBuilder {
        private AppDialog appDialog;
        private Image appIcon = UiUtil.getAppIcon();
        private String title;
        private String headerText;
        private String customText;
        private double width = 350;
        private double height = 150;
        private FontIcon icon;

        private ButtonAction leftBtnAction = () -> appDialog.close();

        private ButtonAction rightBtnAction = () -> appDialog.close();

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
         * @return org.jcnc.jnotepad.ui.dialog.AppDialog.AppDialogBuilder
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
        public AppDialogBuilder setLeftBtnAction(ButtonAction leftBtnAction) {
            if (leftBtnAction != null) {
                this.leftBtnAction = leftBtnAction;
            }
            return this;
        }

        /**
         * 设置右按钮操作
         */
        public AppDialogBuilder setRightBtnAction(ButtonAction rightBtnAction) {
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


        public AppDialog build() {
            appDialog = new AppDialog(this);
            return appDialog;
        }
    }

}
