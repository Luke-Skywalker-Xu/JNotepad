package org.jcnc.jnotepad.ui;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.status.JNotepadStatusBox;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 许轲
 */
public class LineNumberTextArea extends BorderPane {
    /**
     * 是否与本地文件关联
     */
    private boolean isRelevance = false;
    private final TextArea mainTextArea;
    private final TextArea lineNumberArea;

    static final int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};
    private static final int MIN_LINE_NUMBER_WIDTH = 30;

    public LineNumberTextArea() {
        mainTextArea = new TextArea();
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
        setCenter(mainTextArea);
        setLeft(lineNumberArea);

        initListeners();
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
        File file = (File) tab.getUserData();
        String newValue = this.mainTextArea.getText();
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, tab.getCharset()))) {
                writer.write(newValue);
                LogUtil.getLogger(this.getClass()).info("正在自动保存---");
            } catch (IOException ignored) {
                LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
            }
        }
    }

    public boolean isRelevance() {
        return isRelevance;
    }

    public void setRelevance(boolean relevance) {
        isRelevance = relevance;
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
        lineNumberArea.setScrollTop(lineNumberAreaScrollTop - 8);
    }

    public TextArea getMainTextArea() {
        return mainTextArea;
    }

}

