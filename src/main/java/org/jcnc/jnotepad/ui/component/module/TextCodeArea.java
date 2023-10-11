package org.jcnc.jnotepad.ui.component.module;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.jcnc.jnotepad.model.entity.DefaultContextMenu;
import org.jcnc.jnotepad.model.entity.VisibleParagraphStyler;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本代码域
 * </p>
 *
 * @author luke
 */
public class TextCodeArea extends CodeArea {

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

    /**
     * 定义用于匹配关键字、括号、分号、字符串和注释的正则表达式模式
     */
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "[()]";
    private static final String BRACE_PATTERN = "[{}]";
    private static final String BRACKET_PATTERN = "[\\[\\]]";
    private static final String SEMICOLON_PATTERN = ";";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN =
            // 用于整体文本处理（文本块）
            "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"
                    // 用于可见段落处理（逐行）
                    + "|" + "/\\*\\V*" + "|" + "^\\h*\\*(\\V*|/)";


    /**
     * 使用正则表达式将关键字、括号、分号、字符串和注释的模式组合成一个复合模式
     */
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
     * 构造函数
     * <p>
     * 用于创建 TextCodeArea 对象
     */
    public TextCodeArea() {
        // 上、右、下、左
        this.setPadding(new Insets(8, 0, 0, 0));

        // 在区域左侧添加行号
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        this.setContextMenu(new DefaultContextMenu());

        /*
          重新计算所有文本的语法高亮，用户停止编辑区域后的500毫秒内
          fixme 这个代码没有作用
         */
        Subscription cleanupWhenNoLongerNeedIt = this
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> this.setStyleSpans(0, computeHighlighting(this.getText())));

        this.getVisibleParagraphs().addModificationObserver
                (
                        new VisibleParagraphStyler<>(this, this::computeHighlighting)
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
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/jcnc/app/css/java_code_styles.css")).toString());
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
        Map<String, String> patternToStyleClass = new HashMap<>(16);
        patternToStyleClass.put("keyword", matcher.group("KEYWORD"));
        patternToStyleClass.put("paren", matcher.group("PAREN"));
        patternToStyleClass.put("brace", matcher.group("BRACE"));
        patternToStyleClass.put("bracket", matcher.group("BRACKET"));
        patternToStyleClass.put("semicolon", matcher.group("SEMICOLON"));
        patternToStyleClass.put("string", matcher.group("STRING"));
        patternToStyleClass.put("comment", matcher.group("COMMENT"));
        for (Map.Entry<String, String> entry : patternToStyleClass.entrySet()) {
            if (entry.getValue() != null) {
                return entry.getKey();
            }
        }
        // 永不发生
        return null;
    }
}
