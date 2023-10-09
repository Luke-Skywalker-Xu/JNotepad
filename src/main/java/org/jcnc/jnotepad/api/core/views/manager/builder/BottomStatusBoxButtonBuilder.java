package org.jcnc.jnotepad.api.core.views.manager.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

/**
 * 下方状态栏按钮建造者
 *
 * @author gewuyou
 */
public class BottomStatusBoxButtonBuilder {
    private Button button;

    private FontIcon fontIcon;

    private EventHandler<ActionEvent> eventHandler;

    public BottomStatusBoxButtonBuilder() {
    }

    public BottomStatusBoxButtonBuilder(Button button) {
        this.button = button;
    }

    public BottomStatusBoxButtonBuilder setFontIcon(FontIcon fontIcon) {
        this.fontIcon = fontIcon;
        return this;
    }

    public BottomStatusBoxButtonBuilder setEventHandler(EventHandler<ActionEvent> eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    public Button build() {
        Optional<Button> container = Optional.ofNullable(button);
        button = container.orElseGet(Button::new);
        button.setGraphic(fontIcon);
        button.setOnAction(eventHandler);
        return button;
    }
}
