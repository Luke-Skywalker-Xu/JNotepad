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

    EventHandler<ActionEvent> getSaveFileEventHandler();

    EventHandler<ActionEvent> getSaveAsFileEventHandler();


    void autoSave(TextArea textArea);

    void updateStatusLabel(TextArea textArea);

    void openAssociatedFile(String filePath);

    void getText(File file);

    void upDateEncodingLabel(String text);

    int getRow(int caretPosition, String text);

    int getColumn(int caretPosition, String text);

    void initTabPane();

    void saveAsFile();

}
