package org.jcnc.jnotepad.ui.module;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.jcnc.jnotepad.util.LogUtil;
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
public class LineNumberTextArea extends StyleClassedTextArea {


    /**
     * 用于记录日志的静态Logger对象
     */
    private static final Logger logger = LogUtil.getLogger(LineNumberTextArea.class);

    /**
     * 构造函数
     * <p>
     * 用于创建 LineNumberTextArea 对象
     */
    public LineNumberTextArea() {
        // 设置 LineNumberTextArea 的样式，包括边框和背景颜色
        getStyleClass().add("line-number-text-area");
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        initListeners();

    }

    /**
     * 初始化监听器方法
     */
    private void initListeners() {
        // 监听主要文本区域的文本变化
        this.textProperty().addListener((observable, oldValue, newValue) -> {
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
        String newValue = this.getText();

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
}