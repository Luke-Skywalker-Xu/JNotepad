package org.jcnc.jnotepad.util;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import javafx.scene.layout.StackPane;
import org.jcnc.jnotepad.views.manager.RootManager;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Collections;

/**
 * 通知实用程序
 *
 * @author gewuyou
 */
public class NotificationUtil {
    private static final RootManager ROOT_MANAGER = RootManager.getInstance();
    private static final StackPane ROOT_STACK_PANE = ROOT_MANAGER.getRootStackPane();

    private NotificationUtil() {

    }

    /**
     * Generates a custom notification with the given message and icon, applying the specified styles.
     *
     * @param message the message to display in the notification
     * @param icon    the icon to display in the notification
     * @param styles  additional styles to apply to the notification
     */
    public static void customNotification(String message, FontIcon icon, String... styles) {
        Notification notification = new Notification(message, icon);
        Collections.addAll(notification.getStyleClass(), styles);
        RootManager.addNotificationToStackPane(ROOT_STACK_PANE, notification, true);
    }

    /**
     * Displays a success notification with the given message.
     *
     * @param message the message to be displayed in the notification
     */
    public static void successNotification(String message) {
        Notification notification = new Notification(message, UiUtil.getSuccessIcon());
        setStyleClass(notification, Styles.SUCCESS);
        RootManager.addNotificationToStackPane(ROOT_STACK_PANE, notification, true);
    }

    /**
     * Generates an info notification with the given message.
     *
     * @param message the message to display in the notification
     */
    public static void infoNotification(String message) {
        Notification notification = new Notification(message, UiUtil.getInfoIcon());
        setStyleClass(notification, Styles.ACCENT);
        RootManager.addNotificationToStackPane(ROOT_STACK_PANE, notification, true);
    }

    /**
     * Generates an error notification with the given message and displays it on the root stack pane.
     *
     * @param message the error message to be displayed
     */
    public static void errorNotification(String message) {
        Notification notification = new Notification(message, UiUtil.getErrorIcon());
        setStyleClass(notification, Styles.DANGER);
        RootManager.addNotificationToStackPane(ROOT_STACK_PANE, notification, true);
    }

    /**
     * Generates a warning notification with the given message and displays it on the root stack pane.
     *
     * @param message the warning message to be displayed
     */
    public static void warningNotification(String message) {
        Notification notification = new Notification(message, UiUtil.getWarningIcon());
        setStyleClass(notification, Styles.WARNING);
        RootManager.addNotificationToStackPane(ROOT_STACK_PANE, notification, true);
    }

    /**
     * Generates a question notification with the given message and displays it on the root stack pane.
     *
     * @param message the question message to be displayed
     */
    public static void questionNotification(String message) {
        Notification notification = new Notification(message, UiUtil.getQuestionIcon());
        setStyleClass(notification, Styles.ACCENT);
        RootManager.addNotificationToStackPane(ROOT_STACK_PANE, notification, true);
    }

    /**
     * Sets the style class of the given notification.
     *
     * @param notification The notification object to set the style class for.
     * @param styleClass   The style class to add to the notification.
     */
    private static void setStyleClass(Notification notification, String styleClass) {
        notification.getStyleClass().add(Styles.ELEVATED_1);
        notification.getStyleClass().add(styleClass);
    }
}
