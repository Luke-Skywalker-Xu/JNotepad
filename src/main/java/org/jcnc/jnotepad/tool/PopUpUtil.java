package org.jcnc.jnotepad.tool;

import javafx.scene.control.Alert;

/**
 * 弹窗工具类
 *
 * @author gewuyou
 */
public class PopUpUtil {

    private static final ThreadLocal<Alert> ERROR_ALERTS = ThreadLocal.withInitial(() -> new Alert(Alert.AlertType.ERROR));

    private PopUpUtil() {
    }

    /**
     * 获取错误弹窗
     *
     * @param title      弹窗标题
     * @param headerText 头文本
     * @param message    信息
     */
    public static void errorAlert(String title, String headerText, String message) {
        Alert alert = ERROR_ALERTS.get();
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
        ERROR_ALERTS.remove();
    }
}
