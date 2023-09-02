package org.jcnc.jnotepad.ui.module;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.root.center.main.bottom.status.JNotepadStatusBox;
import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTab;
import org.jcnc.jnotepad.root.center.main.center.tab.JNotepadTabPane;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.tool.SingletonUtil;
import org.jcnc.jnotepad.tool.UiUtil;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 行号文本区域
 *
 * <p>这个类继承自JavaFX的BorderPane类，用于显示带有行号的文本区域。它包括主要文本区域和行号文本区域。</p>
 *
 * @author luke
 */
public class LineNumberTextArea extends BorderPane {

    static final int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};
    private static final Logger logger = LogUtil.getLogger(LineNumberTextArea.class);
    private static final int MIN_LINE_NUMBER_WIDTH = 30;
    private final TextArea mainTextArea;
    private final TextArea lineNumberArea;

    public LineNumberTextArea() {
        mainTextArea = new TextArea();
        mainTextArea.setWrapText(SingletonUtil.getAppConfigController().getAutoLineConfig());

        lineNumberArea = new TextArea();
        lineNumberArea.setEditable(false);
        lineNumberArea.setPrefWidth(MIN_LINE_NUMBER_WIDTH);
        lineNumberArea.setMinWidth(MIN_LINE_NUMBER_WIDTH);
        // 设定自定义样式
        lineNumberArea.getStyleClass().add("text-line-number");
        mainTextArea.getStyleClass().add("main-text-area");
        this.setStyle(
                "-fx-border-color:white;" +
                        "-fx-background-color:white"
        );

        initListeners();

        setCenter(mainTextArea);
        setLeft(lineNumberArea);
    }

    private void initListeners() {
        // 当主要文本区域的垂直滚动位置发生变化时，使行号文本区域的滚动位置保持一致
        mainTextArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> lineNumberArea.setScrollTop(mainTextArea.getScrollTop()));

        // 当行号文本区域的垂直滚动位置发生变化时，使主要文本区域的滚动位置保持一致
        lineNumberArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> mainTextArea.setScrollTop(lineNumberArea.getScrollTop()));

        lineNumberArea.textProperty().addListener((observable, oldValue, newValue) -> updateLineNumberWidth());

        this.mainTextArea.caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> JNotepadStatusBox.getInstance().updateWordCountStatusLabel());
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            // 更新行号
            updateLineNumberArea();
            // 更新状态栏
            JNotepadStatusBox.getInstance().updateWordCountStatusLabel();
            // 自动保存
            save();
        });
    }

    /**
     * 以原文件编码格式写回文件
     */
    public void save() {
        JNotepadTab tab = JNotepadTabPane.getInstance().getSelected();
        if (tab == null) {
            return;
        }
        File file = (File) tab.getUserData();
        String newValue = this.mainTextArea.getText();
        if (file == null) {
            // 文件对象不存在
            logger.warn("Tab上没有关联文件信息");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, tab.getCharset()))) {
            writer.write(newValue);
            LogUtil.getLogger(this.getClass()).info("正在自动保存---");
        } catch (IOException ignored) {
            LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
    }

    private void updateLineNumberWidth() {
        int numOfLines = mainTextArea.getParagraphs().size();
        int count = 1;
        for (int i = 0; i < SIZE_TABLE.length; i++) {
            if (numOfLines <= SIZE_TABLE[i]) {
                count = i + 1;
                break;
            }
        }
        // 单数字宽度10像素，4为padding=左3+右1
        int actualWidth = Math.max(count * 10 + 11, MIN_LINE_NUMBER_WIDTH);
        if (actualWidth != lineNumberArea.getWidth()) {
            lineNumberArea.setPrefWidth(actualWidth);
        }
    }

    public StringProperty textProperty() {
        return mainTextArea.textProperty();
    }

    private void updateLineNumberArea() {
        // 保存当前的滚动位置
        /*
          更新行号文本区域的内容，根据主要文本区域的段落数生成行号。
         */
        double mainTextAreaScrollTop = mainTextArea.getScrollTop();
        double lineNumberAreaScrollTop = lineNumberArea.getScrollTop();

        int numOfLines = mainTextArea.getParagraphs().size();
        StringBuilder lineNumberText = new StringBuilder();
        for (int i = 1; i <= numOfLines; i++) {
            lineNumberText.append(i).append("\n");
        }
        lineNumberArea.setText(lineNumberText.toString());

        // 恢复之前的滚动位置
        mainTextArea.setScrollTop(mainTextAreaScrollTop);
        lineNumberArea.setScrollTop(lineNumberAreaScrollTop);
    }

    public TextArea getMainTextArea() {
        return mainTextArea;
    }
}
