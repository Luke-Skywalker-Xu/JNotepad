package org.jcnc.jnotepad.ui.module;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.SingletonUtil;
import org.jcnc.jnotepad.views.root.center.main.bottom.status.BottomStatusBox;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;
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

    /**
     * 用于确定行号区域宽度的大小表格，每个元素表示不同的行数范围
     */
    private static final int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    /**
     * 用于记录日志的静态Logger对象
     */
    private static final Logger logger = LogUtil.getLogger(LineNumberTextArea.class);

    /**
     * 行号区域的最小宽度
     */
    private static final int MIN_LINE_NUMBER_WIDTH = 30;

    /**
     * 主文本区域的TextArea实例
     */
    private final TextArea mainTextArea = new TextArea();

    /**
     * 行号区域的TextArea实例
     */
    private final TextArea lineNumberArea = new TextArea();


    /**
     * 构造函数
     * <p>
     * 用于创建 LineNumberTextArea 对象
     */
    public LineNumberTextArea() {
        // 设置主文本区域是否自动换行，根据应用配置决定
        mainTextArea.setWrapText(SingletonUtil.getAppConfigController().getAutoLineConfig());

        // 设置行号区域不可编辑
        lineNumberArea.setEditable(false);

        // 设置行号区域的首选宽度和最小宽度为最小行号宽度
        lineNumberArea.setPrefWidth(MIN_LINE_NUMBER_WIDTH);
        lineNumberArea.setMinWidth(MIN_LINE_NUMBER_WIDTH);

        // 为行号区域和主文本区域添加CSS样式类
        lineNumberArea.getStyleClass().add("text-line-number");
        mainTextArea.getStyleClass().add("main-text-area");

        // 设置 LineNumberTextArea 的样式，包括边框和背景颜色
        this.setStyle(
                "-fx-border-color:white;" +
                        "-fx-background-color:white"
        );

        // 初始化监听器，用于处理事件
        initListeners();

        // 将主文本区域设置为中央内容，将行号区域设置为左侧内容
        setCenter(mainTextArea);
        setLeft(lineNumberArea);
    }


    /**
     * 初始化监听器方法
     */
    private void initListeners() {
        // 监听主要文本区域的滚动位置变化
        mainTextArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> lineNumberArea.setScrollTop(mainTextArea.getScrollTop()));
        // 监听行号文本区域的滚动位置变化
        lineNumberArea.scrollTopProperty().addListener((observable, oldValue, newValue) -> mainTextArea.setScrollTop(lineNumberArea.getScrollTop()));
        // 监听行号文本区域的文本变化
        lineNumberArea.textProperty().addListener((observable, oldValue, newValue) -> updateLineNumberWidth());
        // 监听主要文本区域的光标位置变化
        this.mainTextArea.caretPositionProperty().addListener((caretObservable, oldPosition, newPosition) -> BottomStatusBox.getInstance().updateWordCountStatusLabel());
        // 监听主要文本区域的文本变化
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            updateLineNumberArea();
            BottomStatusBox.getInstance().updateWordCountStatusLabel();
            save();
        });
    }

    /**
     * 保存方法
     */
    public void save() {
        // 获取当前选定的中央标签页（CenterTab对象）
        CenterTab tab = CenterTabPane.getInstance().getSelected();

        // 如果没有选定标签页，返回，不执行保存操作
        if (tab == null) {
            return;
        }

        // 从标签页的用户数据中获取文件对象
        File file = (File) tab.getUserData();

        // 获取主文本区域中的文本内容
        String newValue = this.mainTextArea.getText();

        // 如果文件对象为空，记录警告信息并返回，不执行保存操作
        if (file == null) {
            logger.warn("Tab上没有关联文件信息");
            return;
        }

        // 尝试使用BufferedWriter写入文件内容
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, tab.getCharset()))) {
            // 将新的文本内容写入文件
            writer.write(newValue);
            // 记录保存操作的日志信息
            LogUtil.getLogger(this.getClass()).info("正在自动保存---");
        } catch (IOException ignored) {
            // 如果发生IO异常，记录忽略的日志信息，但不中断程序执行
            LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
    }


    /**
     * 更新行号宽度方法
     */
    private void updateLineNumberWidth() {
        // 获取主文本区域的段落数量，即文本的行数
        int numOfLines = mainTextArea.getParagraphs().size();

        // 初始化一个计数器，用于确定适合的行号宽度
        int count = 1;

        // 遍历行号宽度大小的表格
        for (int i = 0; i < SIZE_TABLE.length; i++) {
            // 检查文本行数是否在当前表格项的范围内
            if (numOfLines <= SIZE_TABLE[i]) {
                // 如果是，设置计数器为当前索引+1并退出循环
                count = i + 1;
                break;
            }
        }

        // 计算实际的行号区域宽度，确保不小于一个最小宽度值
        int actualWidth = Math.max(count * 10 + 11, MIN_LINE_NUMBER_WIDTH);

        // 检查实际宽度是否与当前行号区域的宽度不同
        if (actualWidth != lineNumberArea.getWidth()) {
            // 如果不同，设置行号区域的首选宽度为实际宽度
            lineNumberArea.setPrefWidth(actualWidth);
        }
    }


    /**
     * 获取主要文本区域的text属性
     *
     * @return 主要文本区域的text属性
     */
    public StringProperty textProperty() {
        return mainTextArea.textProperty();
    }

    /**
     * 更新行号区域方法
     */
    private void updateLineNumberArea() {
        // 获取主文本区域的垂直滚动位置
        double mainTextAreaScrollTop = mainTextArea.getScrollTop();

        // 获取行号区域的垂直滚动位置
        double lineNumberAreaScrollTop = lineNumberArea.getScrollTop();

        // 获取主文本区域的段落数量，即文本的行数
        int numOfLines = mainTextArea.getParagraphs().size();

        // 用于构建行号文本的字符串构建器
        StringBuilder lineNumberText = new StringBuilder();

        // 循环迭代，生成行号文本,
        for (int i = 1; i <= numOfLines; i++) {
            // 将行号和换行符添加到字符串中
            lineNumberText.append(i).append("\n");
        }

        // 将生成的行号文本设置到行号区域
        lineNumberArea.setText(lineNumberText.toString());

        // 恢复主文本区域的垂直滚动位置
        mainTextArea.setScrollTop(mainTextAreaScrollTop);

        // 恢复行号区域的垂直滚动位置
        lineNumberArea.setScrollTop(lineNumberAreaScrollTop);
    }


    /**
     * 获取主要文本区域
     *
     * @return 主要文本区域
     */
    public TextArea getMainTextArea() {
        return mainTextArea;
    }
}