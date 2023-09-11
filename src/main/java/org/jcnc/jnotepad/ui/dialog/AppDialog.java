package org.jcnc.jnotepad.ui.dialog;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public AppDialog(
            AppDialogBuilder builder) {
        // 设置窗口图标
        this.getIcons().add(builder.getAppIcon());
        setTitle(builder.getTitle());
        setResizable(builder.isResizable());
        initModality(builder.getModality());

        BorderPane borderPane = createLayout(builder);
        Scene scene = new Scene(borderPane, builder.getWidth(), builder.getHeight());

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
        HBox iconBox = new HBox(builder.getIcon());
        iconBox.setPadding(builder.getIconCoxPaddingInsets());
        VBox vbox = new VBox(builder.getHBoxSpacing());
        vbox.setAlignment(builder.getVboxPos());

        Label label = new Label(builder.getHeaderText());

        // 自定义文本
        Label customTextLabel = new Label(builder.getCustomText());

        Button confirmButton = createButton(builder.getLeftBtnText(), builder.getLeftBtnAction()::handleAction);
        Button cancelButton = createButton(builder.getRightBtnText(), builder.getRightBtnAction()::handleAction);

        HBox hBox = new HBox(builder.getHBoxSpacing(), confirmButton, cancelButton);
        hBox.setAlignment(builder.getHboxPos());
        hBox.setPadding(builder.gethBoxPaddingInsets());
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
}
