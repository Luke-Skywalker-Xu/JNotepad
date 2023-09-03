package org.jcnc.jnotepad.ui.dialog.alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jcnc.jnotepad.tool.UiUtil;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * 自定义提示框的抽象基类
 *
 * <p>该类用于创建自定义的提示框窗口，包括图标、消息文本和确认、取消按钮。</p>
 *
 * @author luke
 */
public class AlertDialog extends Stage {

    /**
     * 构造一个自定义提示框。
     *
     * @param title      提示框的标题
     * @param message    提示框中显示的消息文本
     * @param customText 自定义的文本
     * @param width      提示框的宽度
     * @param height     提示框的高度
     * @param type       提示框的类型
     * @param action     按钮的事件类
     */
    public AlertDialog(String title, String message, String customText, double width, double height, DialogType type, AlertDialogButtonAction action) {
        // 设置窗口图标
        this.getIcons().add(UiUtil.getAppIcon());
        this.customText = customText;
        setTitle(title);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);

        FontIcon icon = switch (type) {
            case INFO -> UiUtil.getInfoIcon();
            case WARNING -> UiUtil.getWarningIcon();
            case ERROR -> UiUtil.getErrorIcon();
            default -> UiUtil.getQuestionIcon();
        };
        BorderPane borderPane = createLayout(message, icon, action);
        Scene scene = new Scene(borderPane, width, height);

        setScene(scene);
    }

    private final String customText;

    /**
     * 构造一个自定义提示框，使用默认大小。
     *
     * @param title      提示框的标题
     * @param message    提示框中显示的消息文本
     * @param customText 自定义的文本
     * @param type       提示框的类型
     * @param action     按钮的事件类
     */
    public AlertDialog(String title, String message, String customText, DialogType type, AlertDialogButtonAction action) {
        // 使用默认的宽度和高度
        this(title, message, customText, 350, 150, type, action);
    }

    /**
     * 创建提示框的布局。
     *
     * @param message 提示框中显示的消息文本
     * @param icon    提示框中显示的图标
     * @return BorderPane 布局容器
     */
    private BorderPane createLayout(String message, FontIcon icon, AlertDialogButtonAction action) {
        BorderPane borderPane = new BorderPane();
        HBox iconBox = new HBox(icon);
        iconBox.setPadding(new Insets(10, 10, 10, 10));
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label(message);

        // 自定义文本
        Label customTextLabel = new Label(customText);

        Button confirmButton = createButton("确认", action::handleConfirmAction);
        Button cancelButton = createButton("取消", action::handleCancelAction);

        HBox hBox = new HBox(10, confirmButton, cancelButton);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, customTextLabel, hBox);

        borderPane.setLeft(iconBox);
        borderPane.setCenter(vbox);
        borderPane.setBottom(hBox);

        return borderPane;
    }


    /**
     * 对话框枚举
     */
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


}
