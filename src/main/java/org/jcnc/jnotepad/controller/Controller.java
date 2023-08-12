package org.jcnc.jnotepad.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.jcnc.jnotepad.Interface.ControllerInterface;
import org.jcnc.jnotepad.LunchApp;

import java.io.*;
import java.util.List;

import static org.jcnc.jnotepad.ViewManager.*;
import static org.jcnc.jnotepad.tool.EncodingDetector.detectEncoding;

public class Controller implements ControllerInterface {

    @Override
    public TextArea openAssociatedFileAndCreateTextArea(List<String> rawParameters) {
        if (!rawParameters.isEmpty()) {
            String filePath = rawParameters.get(0);
            openAssociatedFile(filePath);
        }

        TextArea textArea = createNewTextArea();
        configureTextArea(textArea);

        return textArea;
    }

    @Override
    public EventHandler<ActionEvent> getLineFeedEventHandler(TextArea textArea) {
        return new LineFeedEventHandler(textArea);
    }

    @Override
    public EventHandler<ActionEvent> getNewFileEventHandler(TextArea textArea) {
        return new NewFileEventHandler();
    }

    @Override
    public EventHandler<ActionEvent> getOpenFileEventHandler() {
        return new OpenFileEventHandler();
    }

    @Override
    public void autoSave(TextArea textArea) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            if (tab != null) {
                File file = (File) tab.getUserData();
                if (file != null) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(newValue);
                    } catch (IOException ignored) {

                    }
                }
            }
        });
    }

    @Override
    public EventHandler<ActionEvent> getSaveFileEventHandler() {
        return new SaveFileEventHandler();
    }

    @Override
    public EventHandler<ActionEvent> getSaveAsFileEventHandler() {
        return new SaveAsFileEventHandler();
    }

    @Override
    public void saveAsFile() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("新建文本");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文档", "*.txt"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                saveFile();
            }
        }
    }

    @Override
    public void updateStatusLabel(TextArea textArea) {
        int caretPosition = textArea.getCaretPosition();
        int row = getRow(caretPosition, textArea.getText());
        int column = getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length);
    }

    @Override
    public void openAssociatedFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            LunchApp.isRelevance = false;
            openFile(file);
        }
    }

    @Override
    public void getText(File file) {
        TextArea textArea = createNewTextArea();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line).append("\n");
            }
            String text = textBuilder.toString();

            Platform.runLater(() -> {
                textArea.setText(text);

                Tab tab = createNewTab(file.getName(), textArea);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
                updateStatusLabel(textArea);

                autoSave(textArea);
            });
        } catch (IOException ignored) {

        }
    }

    @Override
    public void upDateEncodingLabel(String text) {
        String encoding = detectEncoding(text);
        enCodingLabel.setText("\t编码: " + encoding);
    }

    @Override
    public int getRow(int caretPosition, String text) {
        caretPosition = Math.min(caretPosition, text.length());
        String substring = text.substring(0, caretPosition);
        int count = 0;
        for (char c : substring.toCharArray()) {
            if (c == '\n') {
                count++;
            }
        }
        return count + 1;
    }

    @Override
    public int getColumn(int caretPosition, String text) {
        return caretPosition - text.lastIndexOf("\n", caretPosition - 1);
    }

    private void configureTextArea(TextArea textArea) {
        textArea.setWrapText(true);
        upDateEncodingLabel(textArea.getText());
        updateStatusLabel(textArea);

        textArea.textProperty().addListener((observable, oldValue, newValue) -> updateStatusLabel(textArea));

        autoSave(textArea);
    }

    private TextArea createNewTextArea() {
        return new TextArea();
    }

    private Tab createNewTab(String tabName, TextArea textArea) {
        Tab tab = new Tab(tabName);
        tab.setContent(textArea);
        tab.setUserData(null);
        return tab;
    }

    private Task<Void> createOpenFileTask(File file) {
        TextArea textArea = createNewTextArea();
        return new Task<>() {
            @Override
            protected Void call() {
                getText(file);
                upDateEncodingLabel(textArea.getText());
                return null;
            }
        };
    }

    private void openFile(File file) {
        Task<Void> openFileTask = createOpenFileTask(file);
        Thread thread = new Thread(openFileTask);
        thread.start();
    }

    private void saveFile() {
        new SaveFileEventHandler();
    }
}
