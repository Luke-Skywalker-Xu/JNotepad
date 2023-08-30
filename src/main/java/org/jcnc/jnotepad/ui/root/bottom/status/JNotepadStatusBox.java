package org.jcnc.jnotepad.ui.root.bottom.status;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.constants.TextConstants;
import org.jcnc.jnotepad.ui.module.AbstractHBox;
import org.jcnc.jnotepad.ui.root.center.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.root.center.tab.JNotepadTabPane;

import java.nio.charset.Charset;

/**
 * 状态栏组件封装。
 * 1. 文字统计
 * 2. 编码
 *
 * @author songdragon
 */
public class JNotepadStatusBox extends AbstractHBox {

    private static final JNotepadStatusBox STATUS_BOX = new JNotepadStatusBox();
    private static final String STATUS_LABEL_FORMAT = "%s : %d \t%s: %d \t%s: %d \t";
    /**
     * 字数统计及光标
     */
    private Label statusLabel;
    /**
     * 显示文本编码
     */
    private Label encodingLabel;


    private JNotepadStatusBox() {
        initStatusBox();
    }

    public static JNotepadStatusBox getInstance() {
        return STATUS_BOX;
    }

    /**
     * 初始化状态栏组件
     *
     * @since 2023/8/27 9:33
     */
    public void initStatusBox() {
        this.getChildren().clear();
        // 创建状态栏
        statusLabel = new Label();
        statusLabel.setText(getStatusBarFormattedText(0, 0, 1));
        // 创建新的标签以显示编码信息
        encodingLabel = new Label();
        updateEncodingLabel();
        updateWhenTabSelected();
        this.getChildren().add(statusLabel);
        this.getChildren().add(encodingLabel);
        this.getProperties().put("borderpane-margin", new Insets(5, 10, 5, 10));

        UiResourceBundle.getInstance().addListener((observable, oldValue, newValue) -> updateWhenTabSelected());

    }

    public void updateEncodingLabel() {
        updateEncodingLabel(null);
    }

    /**
     * 更新编码展示
     *
     * @param encoding 文件编码
     */
    public void updateEncodingLabel(String encoding) {
        if (encoding == null) {
            encoding = Charset.defaultCharset().name();
        }
        this.encodingLabel.setText(getEncodingFormattedText(encoding));
    }

    /**
     * 更新字数统计
     */
    public void updateWordCountStatusLabel() {
        JNotepadTabPane instance = JNotepadTabPane.getInstance();
        if (instance.getSelected() == null) {
            return;
        }
        TextArea textArea = instance.getSelected().getLineNumberTextArea().getMainTextArea();
        int caretPosition = textArea.getCaretPosition();
        int row = getRow(caretPosition, textArea.getText());
        int column = getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        this.statusLabel.setText(getStatusBarFormattedText(row, column, length));
    }

    /**
     * Tab选中时，更新状态栏
     * <br>1. 状态栏更新当前选中tab的数字统计
     * <br>2. 状态栏更新当前选中tab的字符编码
     */
    public void updateWhenTabSelected() {
        JNotepadTabPane instance = JNotepadTabPane.getInstance();
        if (instance.getSelected() != null) {
            updateWordCountStatusLabel();
            JNotepadTab jNotepadTab = instance.getSelected();
            if (jNotepadTab != null) {
                updateEncodingLabel(jNotepadTab.getCharset().name());
            }
        }
    }

    /**
     * 获取光标所在行号。
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     * @return 光标所在行号
     */
    public int getRow(int caretPosition, String text) {
        caretPosition = Math.min(caretPosition, text.length());
        String substring = text.substring(0, caretPosition);
        int count = 0;
        for (char c : substring.toCharArray()) {
            if (c == '\n') {
                count++;
            }
        }
        return count + 1;
    }

    /**
     * 获取光标所在列号。
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     * @return 光标所在列号
     */
    public int getColumn(int caretPosition, String text) {
        return caretPosition - text.lastIndexOf("\n", caretPosition - 1);
    }

    protected String getStatusBarFormattedText(int row, int column, int wordCount) {
        String rowText = UiResourceBundle.getContent(TextConstants.ROW);
        String columnText = UiResourceBundle.getContent(TextConstants.COLUMN);
        String wordCountText = UiResourceBundle.getContent(TextConstants.WORD_COUNT);
        return String.format(STATUS_LABEL_FORMAT,
                rowText,
                row,
                columnText,
                column,
                wordCountText,
                wordCount
        );
    }

    protected String getEncodingFormattedText(String encoding) {
        String encodingLabelFormat = "\t%s : %s";
        return String.format(encodingLabelFormat, UiResourceBundle.getContent(TextConstants.ENCODE), encoding);
    }
}
