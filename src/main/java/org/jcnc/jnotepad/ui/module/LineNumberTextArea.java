package org.jcnc.jnotepad.ui.module;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.views.manager.BottomStatusBoxManager;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.reactfx.Subscription;
import org.reactfx.collection.ListModification;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 行号文本区域
 *
 * <p>这个类继承自JavaFX的BorderPane类，用于显示带有行号的文本区域。它包括主要文本区域和行号文本区域。</p>
 *
 * @author luke
 */
public class LineNumberTextArea extends CodeArea {

    private static final String[] KEYWORDS = new String[]{
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    // 定义用于匹配关键字、括号、分号、字符串和注释的正则表达式模式
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"   // 用于整体文本处理（文本块）
            + "|" + "/\\*[^\\v]*" + "|" + "^\\h*\\*([^\\v]*|/)";  // 用于可见段落处理（逐行）

    // 使用正则表达式将关键字、括号、分号、字符串和注释的模式组合成一个复合模式
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

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
        // 上、右、下、左
        this.setPadding(new Insets(8, 0, 0, 0));

        // 在区域左侧添加行号
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        this.setContextMenu(new DefaultContextMenu());

        // 重新计算所有文本的语法高亮，用户停止编辑区域后的500毫秒内
        Subscription cleanupWhenNoLongerNeedIt = this
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> this.setStyleSpans(0, computeHighlighting(this.getText())));

        this.getVisibleParagraphs().addModificationObserver
                (
                        new LineNumberTextArea.VisibleParagraphStyler<>(this, this::computeHighlighting)
                );

        // 自动缩进：在按下回车键时插入上一行的缩进
        final Pattern whiteSpace = Pattern.compile("^\\s+");
        this.addEventHandler(KeyEvent.KEY_PRESSED, kE ->
        {
            if (kE.getCode() == KeyCode.ENTER) {
                int caretPosition = this.getCaretPosition();
                int currentParagraph = this.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher(this.getParagraph(currentParagraph - 1).getSegments().get(0));
                if (m0.find()) {
                    Platform.runLater(() -> this.insertText(caretPosition, m0.group()));
                }
            }
        });

        initListeners();
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/java_code_styles.css")).toString());

    }


    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = getStyleClass(matcher);
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static String getStyleClass(Matcher matcher) {
        String styleClass =
                matcher.group("KEYWORD") != null ? "keyword" :
                        matcher.group("PAREN") != null ? "paren" :
                                matcher.group("BRACE") != null ? "brace" :
                                        matcher.group("BRACKET") != null ? "bracket" :
                                                matcher.group("SEMICOLON") != null ? "semicolon" :
                                                        matcher.group("STRING") != null ? "string" :
                                                                matcher.group("COMMENT") != null ? "comment" :
                                                                        null; /* 永远不会发生 */
        assert styleClass != null;
        return styleClass;
    }

    static class VisibleParagraphStyler<PS, SEG, S> implements Consumer<ListModification<? extends Paragraph<PS, SEG, S>>> {
        private final GenericStyledArea<PS, SEG, S> area;
        private final Function<String,StyleSpans<S>> computeStyles;
        private int prevParagraph, prevTextLength;

        public VisibleParagraphStyler( GenericStyledArea<PS, SEG, S> area, Function<String,StyleSpans<S>> computeStyles )
        {
            this.computeStyles = computeStyles;
            this.area = area;
        }

        @Override
        public void accept( ListModification<? extends Paragraph<PS, SEG, S>> lm )
        {
            if ( lm.getAddedSize() > 0 ) Platform.runLater( () ->
            {
                int paragraph = Math.min( area.firstVisibleParToAllParIndex() + lm.getFrom(), area.getParagraphs().size()-1 );
                String text = area.getText( paragraph, 0, paragraph, area.getParagraphLength( paragraph ) );

                if ( paragraph != prevParagraph || text.length() != prevTextLength )
                {
                    if ( paragraph < area.getParagraphs().size()-1 )
                    {
                        int startPos = area.getAbsolutePosition( paragraph, 0 );
                        area.setStyleSpans( startPos, computeStyles.apply( text ) );
                    }
                    prevTextLength = text.length();
                    prevParagraph = paragraph;
                }
            });
        }
    }

    private class DefaultContextMenu extends ContextMenu {
        private MenuItem fold, unfold, print;

        public DefaultContextMenu() {
            fold = new MenuItem("折叠所选文本");
            fold.setOnAction(AE -> {
                hide();
                fold();
            });

            unfold = new MenuItem("从光标处展开");
            unfold.setOnAction(AE -> {
                hide();
                unfold();
            });

            print = new MenuItem("打印");
            print.setOnAction(AE -> {
                hide();
                print();
            });

            getItems().addAll(fold, unfold, print);
        }

        /**
         * 折叠多行所选文本，仅显示第一行并隐藏其余部分。
         */
        private void fold() {
            ((CodeArea) getOwnerNode()).foldSelectedParagraphs();
        }

        /**
         * 展开当前行/段落（如果有折叠）。
         */
        private void unfold() {
            CodeArea area = (CodeArea) getOwnerNode();
            area.unfoldParagraphs(area.getCurrentParagraph());
        }

        private void print() {
            System.out.println(((CodeArea) getOwnerNode()).getText());
        }
    }

    /**
     * 初始化监听器方法
     */
    private void initListeners() {
        // 监听主要文本区域的文本变化
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            BottomStatusBoxManager.getInstance().updateWordCountStatusLabel();
            save();
        });
    }

    /**
     * 保存方法
     */
    public void save() {
        // 获取当前选定的中央标签页（CenterTab对象）
        CenterTab tab = CenterTabPaneManager.getInstance().getSelected();

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
            // 如果发生IO异常，记录忽视的日志信息，但不中断程序执行
            LogUtil.getLogger(this.getClass()).info("已忽视IO异常!");
        }
    }
}
