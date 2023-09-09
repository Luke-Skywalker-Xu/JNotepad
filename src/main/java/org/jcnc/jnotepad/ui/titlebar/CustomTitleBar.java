package org.jcnc.jnotepad.ui.titlebar;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 自定义标题栏组件，用于自定义窗口标题栏的显示和交互。
 * 该类提供了窗口图标、标题文本以及最小化、最大化和关闭按钮的功能。
 *
 * @author 许轲
 */
public class CustomTitleBar extends HBox {
    private static CustomTitleBar instance;

    private CustomTitleBar() {
        // 设置样式和布局
        this.setAlignment(Pos.CENTER);

        // 左侧图标
        ImageView iconImageView = new ImageView(new Image("icon.png"));
        iconImageView.setFitWidth(30);
        iconImageView.setFitHeight(30);

        Label titleLabel = new Label("自定义标题栏");
        titleLabel.setStyle("-fx-font-size: 18px;");

        // 右侧按钮区域
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // 最小化按钮
        Button minimizeButton = new Button("-");
        minimizeButton.setStyle("-fx-background-color: transparent; ");
        minimizeButton.setOnAction(event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setIconified(true); // 最小化窗口
        });

        // 最大化/还原按钮
        Button maximizeButton = new Button("□");
        maximizeButton.setStyle("-fx-background-color: transparent;");
        maximizeButton.setOnAction(event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setMaximized(!stage.isMaximized()); // 最大化/还原窗口
        });

        // 关闭按钮
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent;");
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
        });

        buttonBox.getChildren().addAll(minimizeButton, maximizeButton, closeButton);

        this.getChildren().addAll(iconImageView, titleLabel, buttonBox);
    }

    /**
     * 获取标题栏的单例实例
     *
     * @return CustomTitleBar的单例实例
     */
    public static CustomTitleBar getInstance() {
        if (instance == null) {
            instance = new CustomTitleBar();
        }
        return instance;
    }

    /**
     * 使窗口可拖动
     *
     * @param stage 要拖动的窗口的Stage对象
     */
    public void makeDraggable(Stage stage) {
        // 创建一个平移动画，设置持续时间和目标位置
        TranslateTransition minimizeAnimation = new TranslateTransition(Duration.seconds(0.3), stage.getScene().getRoot());
        // 将根节点向下平移，使窗口消失
        minimizeAnimation.setToY(stage.getHeight());

        final Delta dragDelta = new Delta();

        // 当鼠标按下时记录初始偏移量
        this.setOnMousePressed(mouseEvent -> {
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
        });

        // 当鼠标拖动时，根据偏移量更新舞台的位置
        this.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }

    private static class Delta {
        double x, y;
    }
}
