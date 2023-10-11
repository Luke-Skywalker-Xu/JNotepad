package org.jcnc.jnotepad.ui.component.stage.topmenu.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * 舞台按钮建造者
 *
 * @author gewuyou
 */
public class StageButtonBuilder {
    private final Button button = new Button();

    private String text;

    private EventHandler<ActionEvent> eventEventHandler;

    public StageButtonBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public StageButtonBuilder setEventEventHandler(EventHandler<ActionEvent> eventEventHandler) {
        this.eventEventHandler = eventEventHandler;
        return this;
    }

    public Button build() {
        button.setText(text);
        button.setOnAction(eventEventHandler);
        return button;
    }
}
