package org.jcnc.jnotepad.ui.module;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 终端仿真器组件，用于执行命令并显示输出。
 * <p>
 * 该组件包括一个用于输入命令的文本字段和一个显示命令输出的CodeArea。
 * 用户可以在文本字段中输入命令，按回车键执行，并在CodeArea中查看输出。
 *
 * @author luke
 */
public class TerminalEmulatorComponent extends BorderPane {

    private CodeArea terminalOutput;

    /**
     * 创建一个新的终端仿真器组件。
     */
    public TerminalEmulatorComponent() {
        init();
    }

    private void init() {
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

        setCenter(terminalOutput);
        setBottom(commandInput);
    }

    private void executeCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

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
