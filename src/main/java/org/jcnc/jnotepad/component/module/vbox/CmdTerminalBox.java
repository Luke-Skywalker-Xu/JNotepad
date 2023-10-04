package org.jcnc.jnotepad.component.module.vbox;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.jcnc.jnotepad.util.LogUtil;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 命令行终端界面。
 *
 * @author luke
 */
public class CmdTerminalBox extends VBox {

    /**
     * 用于显示命令输出的文本区域
     */
    private final StyleClassedTextArea cmdOutput;
    /**
     * 用户输入命令的文本框
     */
    private final TextField cmdInput;
    /**
     * 用于运行命令的进程
     */
    private Process cmdProcess;
    /**
     * 用于向命令进程发送输入的写入器
     */
    private PrintWriter cmdInputWriter;

    String currentDirectory;

    /**
     * 创建CmdTerminal对象的构造函数。
     */
    public CmdTerminalBox() {
        // 创建UI元素
        cmdOutput = new StyleClassedTextArea();
        cmdInput = new TextField();

        // 设置CodeArea的大小策略和边距
        // 让CodeArea在垂直方向上扩展以填充剩余空间
        VBox.setVgrow(cmdOutput, Priority.ALWAYS);
        // 设置CodeArea的边距
        VBox.setMargin(cmdOutput, new Insets(8));

        // 添加输入框的Enter键监听器，按下Enter执行命令
        cmdInput.setOnAction(e -> executeCommand());

        // 创建布局
        getChildren().addAll(cmdOutput, cmdInput);

        // 获取当前工作目录的文件夹路径
        currentDirectory = System.getProperty("user.dir");


        // 创建并启动cmd进程，使用GBK字符编码
        try {
            cmdProcess = new ProcessBuilder("cmd.exe")
                    // 设置CMD工作目录为当前文件夹路径
                    .directory(new File(currentDirectory))
                    .redirectErrorStream(true)
                    .start();
            cmdInputWriter = new PrintWriter(new OutputStreamWriter(cmdProcess.getOutputStream(), Charset.forName("GBK")));
        } catch (IOException e) {
            LogUtil.getLogger(this.getClass()).info("已调用, {}", cmdProcess);
        }


        // 延迟执行打印当前文件夹路径的语句
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            appendTextToCmdOutput(currentDirectory + ">" + "\n");

        }));
        timeline.setCycleCount(1);
        timeline.play();

        // 读取cmd的输出并显示在UI中
        InputStream cmdOutputInputStream = cmdProcess.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(cmdOutputInputStream, Charset.forName("GBK")));

        Thread cmdOutputThread = getCmdOutputThread(reader);
        cmdOutputThread.start();

        // 关闭cmd进程
        Platform.runLater(() -> getScene().getWindow().setOnCloseRequest(e -> {
            if (cmdProcess != null) {
                cmdProcess.destroy();
            }
        }));
    }

    private Thread getCmdOutputThread(BufferedReader reader) {
        Task<Void> cmdOutputTask = new Task<>() {
            @Override
            protected Void call() {
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        final String outputLine = line + "\n";
                        Platform.runLater(() -> appendTextToCmdOutput(outputLine));
                    }
                } catch (IOException e) {
                    LogUtil.getLogger(this.getClass()).info("已调用, {}", this);
                }
                return null;
            }
        };
        Thread cmdOutputThread = new Thread(cmdOutputTask);
        cmdOutputThread.setDaemon(true);
        return cmdOutputThread;
    }

    /**
     * 执行用户输入的命令。
     */
    private void executeCommand() {
        String command = cmdInput.getText();
        if (cmdProcess != null && command != null) {
            cmdInputWriter.println(command);
            cmdInputWriter.flush();
            cmdInput.clear();
        }
    }

    /**
     * 将文本追加到命令输出文本区域，并滚动到最后一行。
     *
     * @param text 要追加的文本
     */
    private void appendTextToCmdOutput(String text) {
        Platform.runLater(() -> {
            cmdOutput.appendText(text);
            // 将滚动条滚动到最后一行
            cmdOutput.moveTo(cmdOutput.getLength());
            cmdOutput.requestFollowCaret();
        });
    }
}
