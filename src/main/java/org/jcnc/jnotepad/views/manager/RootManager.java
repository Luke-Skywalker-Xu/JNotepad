package org.jcnc.jnotepad.views.manager;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.views.root.RootBorderPane;

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
    private BorderPane root;

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
        BorderPane root = new BorderPane();
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
    public void addNotificationToStackPane(StackPane stackPane, Notification msg) {
        msg.getStyleClass().addAll(Styles.ACCENT, Styles.ELEVATED_1);
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

}
