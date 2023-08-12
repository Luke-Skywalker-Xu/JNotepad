package org.jcnc.jnotepad.Interface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

import java.io.File;
import java.util.List;

public interface ControllerInterface {

    TextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters);

    EventHandler<ActionEvent> getLineFeedEventHandler(TextArea textArea);

    EventHandler<ActionEvent> getNewFileEventHandler(TextArea textArea);

    EventHandler<ActionEvent> getOpenFileEventHandler();

    void autoSave(TextArea textArea);

    EventHandler<ActionEvent> getSaveFileEventHandler();

    EventHandler<ActionEvent> getSaveAsFileEventHandler();

    void saveAsFile();

    void updateStatusLabel(TextArea textArea);

    void openAssociatedFile(String filePath);

    void getText(File file);

    void upDateEncodingLabel(String text);

    int getRow(int caretPosition, String text);

    int getColumn(int caretPosition, String text);
}
