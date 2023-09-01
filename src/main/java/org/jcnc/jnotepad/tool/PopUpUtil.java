package org.jcnc.jnotepad.tool;

import javafx.scene.control.Alert;

/**
 * 弹窗工具类
 *
 * @author gewuyou
 */
public class PopUpUtil {

    private PopUpUtil() {
    }

    /**
     * 获取错误弹窗。
     *
     * @param title      弹窗标题
     * @param headerText 头文本
     * @param message    信息
     *
     * <p>用于创建一个错误类型的弹窗，显示给用户以指示发生了错误。</p>
     */
    public static void errorAlert(String title, String headerText, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
