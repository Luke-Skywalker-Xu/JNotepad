package org.jcnc.jnotepad.ui;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class LineNumberTextArea extends BorderPane {


    private final TextArea mainTextArea;
    private final TextArea lineNumberArea;

    static final int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    public LineNumberTextArea() {
        mainTextArea = new TextArea();
        lineNumberArea = new TextArea();
        lineNumberArea.setEditable(false);
        lineNumberArea.setPrefWidth(30);
        mainTextArea.setStyle("-fx-border-color:white;-fx-background-color:white;");
        // lineNumberArea.setStyle("-fx-border-color:white;-fx-background-color:white;");
        // 设置显示滚动条样式类
        lineNumberArea.getStyleClass().add("text-line-number");
        lineNumberArea.textProperty().addListener((observable, oldValue, newValue) -> updateLineNumberWidth());
        mainTextArea.textProperty().addListener((observable, oldValue, newValue) -> updateLineNumberArea());

        // 当主要文本区域的垂直滚动位置发生变化时，使行号文本区域的滚动位置保持一致
        mainTextArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> {
            lineNumberArea.setScrollTop(mainTextArea.getScrollTop());
        });

        // 当行号文本区域的垂直滚动位置发生变化时，使主要文本区域的滚动位置保持一致
        lineNumberArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> {
            mainTextArea.setScrollTop(lineNumberArea.getScrollTop());
        });

        setCenter(mainTextArea);
        setLeft(lineNumberArea);
    }

    private void updateLineNumberWidth() {
        int numOfLines = mainTextArea.getParagraphs().size();
        int count = 1;
        for (int i = 0; i < sizeTable.length; i++) {
            if (numOfLines <= sizeTable[i]) {
                count = i + 1;
                break;
            }
        }
        //单数字宽度10像素，4为padding=左3+右1
        int actualWidth = count * 10 + 4;
        if (actualWidth > lineNumberArea.getWidth()) {
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

