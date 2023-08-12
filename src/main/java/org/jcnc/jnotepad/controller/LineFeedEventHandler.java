package org.jcnc.jnotepad.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

public class LineFeedEventHandler implements EventHandler<ActionEvent> {
    private final TextArea textArea;

    public LineFeedEventHandler(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void handle(ActionEvent event) {
        String text = textArea.getText();
        textArea.setText(text + "\n");
    }
}
