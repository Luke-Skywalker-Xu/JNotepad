package org.jcnc.jnotepad.ui.module.alert;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jcnc.jnotepad.interfaces.CustomDialogAble;

/**
 * 自定义提示框的抽象基类
 *
 * <p>该类用于创建自定义的提示框窗口，包括图标、消息文本和确认、取消按钮。</p>
 *
 * @author luke
 */
public abstract class AbstractDialog extends Stage implements CustomDialogAble {

    private ImageView iconImageView;
    private final String customText;

    /**
     * 构造一个自定义提示框。
     *
     * @param message    提示框中显示的消息文本
     * @param customText 自定义的文本
     * @param width      提示框的宽度
     * @param height     提示框的高度
     */
    protected AbstractDialog(String message, String customText, double width, double height) {
        this.customText = customText;
        setTitle(getAlertType());
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);

        BorderPane borderPane = createLayout(message);
        Scene scene = new Scene(borderPane, width, height);

        setScene(scene);
    }

    /**
     * 处理取消按钮的操作。
     */
    @Override
    public void handleCancelAction() {
        close();
    }

    /**
     * 创建提示框的布局。
     *
     * @param message 提示框中显示的消息文本
     * @return BorderPane 布局容器
     */
    private BorderPane createLayout(String message) {
        BorderPane borderPane = new BorderPane();
        iconImageView = new ImageView();

        HBox iconBox = new HBox(iconImageView);
        iconBox.setPadding(new Insets(10, 10, 10, 10));
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Label label = new Label(message);

        // 自定义文本
        Label customTextLabel = new Label(customText);

        Button confirmButton = createButton("确认", this::handleConfirmAction);
        Button cancelButton = createButton("取消", this::handleCancelAction);

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
     * 设置提示框的图标。
     *
     * @param iconImage 图标图像
     */
    @Override
    public void setIconImage(Image iconImage) {
        iconImageView.setImage(iconImage);
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

    /**
     * 获取提示框类型的抽象方法，子类应该实现该方法以返回具体的提示框类型。
     *
     * @return 提示框类型
     */
    @Override
    public abstract String getAlertType();

    /**
     * 处理确认按钮的操作，子类必须实现。
     */
    @Override
    public abstract void handleConfirmAction();

    /**
     * 构造一个自定义提示框，使用默认大小。
     *
     * @param message    提示框中显示的消息文本
     * @param customText 自定义的文本
     */
    protected AbstractDialog(String message, String customText) {
        // 使用默认的宽度和高度
        this(message, customText, 350, 150);
    }
}
