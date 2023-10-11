package org.jcnc.jnotepad.ui.views.manager;

import atlantafx.base.controls.Notification;
import atlantafx.base.util.Animations;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.jcnc.jnotepad.controller.exception.AppException;
import org.jcnc.jnotepad.ui.views.root.RootBorderPane;

/**
 * 根布局管理器类，用于管理记事本应用程序的根布局组件。
 *
 * <p>该类负责管理应用程序的视图组件，包括主界面布局</p>
 *
 * @author 许轲
 */
public class RootManager {

    private static RootManager instance = null;
    StackPane rootStackPane;
    /**
     * 主布局
     */
    private final BorderPane root;

    /**
     * 私有构造函数。设置场景和根布局。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     */
    private RootManager(Scene scene) {
        root = new BorderPane();
        scene.setRoot(root);
    }

    /**
     * 获取 RootManager 的实例。如果实例不存在，则创建一个新实例。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     * @return RootManager 的实例。
     */
    public static RootManager getInstance(Scene scene) {
        if (instance == null) {
            instance = new RootManager(scene);
        }
        return instance;
    }

    /**
     * 获取 RootManager 的实例。
     *
     * @return RootManager 的实例。
     * @throws AppException 如果实例未初始化。
     */
    public static RootManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new AppException("RootManager 的实例未初始化!");
        }
    }


    /**
     * 初始化屏幕组件。
     *
     * @param scene 与视图相关联的 JavaFX 场景。
     */
    public void initScreen(Scene scene) {
        rootStackPane = new StackPane();

        // 创建主界面布局
        root.setCenter(RootBorderPane.getInstance());

        rootStackPane.getChildren().addAll(root);
        scene.setRoot(rootStackPane);

    }

    /**
     * 将提示框添加到 StackPane 中。
     *
     * @param stackPane 要添加提示框的 StackPane。
     * @param msg       要显示的提示框。
     */
    public static void addNotificationToStackPane(StackPane stackPane, Notification msg, int closeDelaySeconds) {
        addNotificationToStackPane(stackPane, msg);
        automaticallyCloseNotification(stackPane, msg, closeDelaySeconds);
    }

    /**
     * Adds a notification to the given StackPane.
     *
     * @param stackPane the StackPane to add the notification to
     * @param msg       the notification message
     * @param autoClose true if the notification should automatically close, false otherwise
     */
    public static void addNotificationToStackPane(StackPane stackPane, Notification msg, boolean autoClose) {
        addNotificationToStackPane(stackPane, msg);
        if (autoClose) {
            automaticallyCloseNotification(stackPane, msg, 3);
        }

    }

    /**
     * Adds a notification message to a StackPane.
     *
     * @param stackPane the StackPane to add the notification to
     * @param msg       the notification message to be added
     */
    private static void addNotificationToStackPane(StackPane stackPane, Notification msg) {
        msg.setPrefHeight(Region.USE_PREF_SIZE);
        msg.setMaxHeight(Region.USE_PREF_SIZE);
        StackPane.setAlignment(msg, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(msg, new Insets(5, 10, 35, 0));

        msg.setOnClose(e -> {
            var out = Animations.slideOutUp(msg, Duration.millis(250));
            out.setOnFinished(f -> stackPane.getChildren().remove(msg));
            out.playFromStart();
        });

        var in = Animations.slideInDown(msg, Duration.millis(250));
        if (!stackPane.getChildren().contains(msg)) {
            stackPane.getChildren().add(msg);
        }
        in.playFromStart();
    }

    /**
     * Automatically closes the notification after a specified delay.
     *
     * @param stackPane         the StackPane containing the notification
     * @param msg               the notification to be closed
     * @param closeDelaySeconds the delay time in seconds before closing the notification
     */
    private static void automaticallyCloseNotification(StackPane stackPane, Notification msg, int closeDelaySeconds) {
        // 自动关闭提示框
        // 设置关闭延迟时间（单位：秒）
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(closeDelaySeconds), event -> {
            // 关闭提示框
            var out = Animations.slideOutUp(msg, Duration.millis(450));
            out.setOnFinished(f -> stackPane.getChildren().remove(msg));
            out.playFromStart();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public StackPane getRootStackPane() {
        return rootStackPane;
    }
}
