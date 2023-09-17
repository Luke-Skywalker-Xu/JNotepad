package org.jcnc.jnotepad.ui.module;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalEmulatorComponent extends VBox {

    private CodeArea terminalOutput;
    private Process process;

    public TerminalEmulatorComponent() {
        initializeUI();
    }

    private void initializeUI() {
        terminalOutput = new CodeArea();
        TextField commandInput = new TextField();

        // 设置行号
        terminalOutput.setParagraphGraphicFactory(LineNumberFactory.get(terminalOutput));

        commandInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String command = commandInput.getText();
                executeCommand(command);
                commandInput.clear();
            }
        });

        getChildren().addAll(terminalOutput, commandInput);
        setAlignment(Pos.CENTER);

        // 设置布局样式和大小
        setSpacing(10);
        setMinSize(800, 600);
    }

    private void executeCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                appendToTerminalOutput(line + "\n");
            }

            int exitCode = process.waitFor();
            appendToTerminalOutput("Exit Code: " + exitCode + "\n");

        } catch (IOException | InterruptedException e) {
            appendToTerminalOutput("Error: " + e.getMessage() + "\n");
        }
    }

    private void appendToTerminalOutput(String text) {
        Platform.runLater(() -> {
            terminalOutput.appendText(text);
            // 将滚动条滚动到最后一行
            terminalOutput.moveTo(terminalOutput.getLength());
            terminalOutput.requestFollowCaret();
        });
    }
}
