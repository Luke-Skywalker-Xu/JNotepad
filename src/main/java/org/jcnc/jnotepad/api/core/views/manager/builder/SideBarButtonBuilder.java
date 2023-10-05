package org.jcnc.jnotepad.api.core.views.manager.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * 侧边栏按钮建造者
 *
 * @author gewuyou
 */
public class SideBarButtonBuilder {
    private Button button;
    private ImageView imageView;
    private EventHandler<ActionEvent> eventHandler;

    public Button build() {
        Optional<Button> container = Optional.of(new Button());
        button = container.get();
        button.setGraphic(imageView);
        button.setOnAction(eventHandler);
        return button;
    }

    public SideBarButtonBuilder setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public SideBarButtonBuilder setButtonEssentialAttribute(Double relativelyPrefWidth, Double relativelyPrefHeight) {
        Optional<Double> container = Optional.of(relativelyPrefHeight);
        button.setPrefWidth(imageView.getFitWidth() + container.orElse(20D));
        container = Optional.of(relativelyPrefWidth);
        button.setPrefHeight(imageView.getFitHeight() + container.orElse(20D));
        return this;
    }

    /**
     * 设置ImageView属性
     *
     * @param fitWidth      适合宽度
     * @param fitHeight     适合高度
     * @param preserveRatio 保持比例
     * @param scaleX        X轴比例
     * @param scaleY        Y轴比例
     * @return 建造者对象
     */
    public SideBarButtonBuilder setImageViewEssentialAttribute(Double fitWidth, Double fitHeight, boolean preserveRatio, Double scaleX, Double scaleY) {
        Optional<Double> container = Optional.ofNullable(fitWidth);
        imageView.setFitWidth(container.orElse(10D));
        container = Optional.ofNullable(fitHeight);
        imageView.setFitHeight(container.orElse(10D));
        imageView.setPreserveRatio(preserveRatio);
        container = Optional.ofNullable(scaleX);
        imageView.setScaleX(container.orElse(2.5));
        container = Optional.ofNullable(scaleY);
        imageView.setScaleY(container.orElse(2.5));
        return this;
    }


    public SideBarButtonBuilder setEventHandler(EventHandler<ActionEvent> eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    public SideBarButtonBuilder setButton(Button button) {
        this.button = button;
        return this;
    }
}
