package org.jcnc.jnotepad.component.stage.setting;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * 设置组件类，用于创建一个包含标签和按钮的垂直布局。
 *
 * <p>
 * 此组件可用于用户界面，提供了设置标签文本、按钮文本和按钮点击事件处理程序的方法。
 * </p>
 *
 * <p>
 * 示例用法:
 * <pre>{@code
 * SettingsComponent settings = new SettingsComponent("标签文本", "按钮文本");
 * settings.setButtonAction(event -> {
 *     // 处理按钮点击事件的逻辑
 * });
 * }</pre>
 * </p>
 *
 * @author luke
 * @since 1.0.0
 */
public class SetDevBox extends VBox {
    private final Label label;
    private final Button button;

    /**
     * 创建一个设置组件实例。
     *
     * @param labelText  标签文本
     * @param buttonText 按钮文本
     */
    public SetDevBox(String labelText, String buttonText) {
        setSpacing(10);

        // 初始化标签
        label = new Label(labelText);

        // 创建按钮
        button = new Button(buttonText);

        // 将标签和按钮添加到垂直布局
        getChildren().addAll(label, button);
    }

    /**
     * 设置标签的文本。
     *
     * @param text 新的标签文本
     */
    public void setLabelText(String text) {
        label.setText(text);
    }

    /**
     * 设置按钮的文本。
     *
     * @param text 新的按钮文本
     */
    public void setButtonText(String text) {
        button.setText(text);
    }

    /**
     * 设置按钮的点击事件处理程序。
     *
     * @param eventHandler 按钮点击事件处理程序
     */
    public void setButtonAction(EventHandler<ActionEvent> eventHandler) {
        button.setOnAction(eventHandler);
    }
}
