package org.jcnc.jnotepad.ui.status;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;

import java.nio.charset.Charset;

/**
 * 状态栏组件封装。
 * 1. 文字统计
 * 2. 编码
 *
 * @author songdragon
 */
public class JNotepadStatusBox extends HBox {

    private static final JNotepadStatusBox STATUS_BOX = new JNotepadStatusBox();

    /**
     * 字数统计及光标
     */
    private final Label statusLabel;

    /**
     * 显示文本编码
     */
    private final Label enCodingLabel; // 显示文本编码

    private JNotepadStatusBox() {
        // 创建状态栏
        statusLabel = new Label("行数：1 \t列数：1 \t字数：0 ");
        // 创建新的标签以显示编码信息
        enCodingLabel = new Label();

        this.getChildren().add(statusLabel);
        this.getChildren().add(enCodingLabel);
        this.getProperties().put("borderpane-margin", new Insets(5, 10, 5, 10));

    }

    public static JNotepadStatusBox getInstance() {
        return STATUS_BOX;
    }

    public void updateEncodingLabel() {
        updateEncodingLabel(null);
    }

    /**
     * 更新编码展示
     * @param encoding 文件编码
     */
    public void updateEncodingLabel(String encoding) {
        if (encoding == null) {
            encoding = Charset.defaultCharset().name();
        }
        this.enCodingLabel.setText("\t编码: " + encoding);
    }

    /**
     * 更新字数统计
     */
    public void updateWordCountStatusLabel() {
        TextArea textArea = JNotepadTabPane.getInstance().getSelected().getLineNumberTextArea().getMainTextArea();
        int caretPosition = textArea.getCaretPosition();
        int row = getRow(caretPosition, textArea.getText());
        int column = getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        this.statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length);
    }

    /**
     * Tab选中时，更新状态栏
     * 1. 状态栏更新当前选中tab的数字统计
     * 2. 状态栏更新当前选中tab的字符编码
     */
    public void updateWhenTabSelected() {
        updateWordCountStatusLabel();
        updateEncodingLabel(JNotepadTabPane.getInstance().getSelected().getCharset().name());
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

}
