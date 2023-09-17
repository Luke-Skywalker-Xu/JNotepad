package org.jcnc.jnotepad.views.root.center.main.bottom.status;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.ui.module.AbstractHorizontalBox;
import org.jcnc.jnotepad.ui.module.LineNumberTextArea;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;

import java.nio.charset.Charset;

/**
 * 状态栏组件封装。
 * 1. 文字统计
 * 2. 编码
 *
 * @author songdragon
 */
public class BottomStatusBox extends AbstractHorizontalBox {

    private static final BottomStatusBox STATUS_BOX = new BottomStatusBox();
    private static final String STATUS_LABEL_FORMAT = "%s : %d \t%s: %d \t%s: %d \t";
    /**
     * 字数统计及光标
     */
    private Label statusLabel;
    /**
     * 显示文本编码
     */
    private Label encodingLabel;


    private BottomStatusBox() {
        initStatusBox();
    }

    public static BottomStatusBox getInstance() {
        return STATUS_BOX;
    }

    /**
     * 初始化状态栏组件
     */
    public void initStatusBox() {
        this.setStyle("-fx-background-color: rgba(43,43,43,0.12);");

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
        this.setAlignment(Pos.BASELINE_RIGHT);
        UiResourceBundle.getInstance().addListener((observable, oldValue, newValue) -> updateWhenTabSelected());

        /*
          第一个参数 10 表示上边距。
          第二个参数 10 表示右边距。
          第三个参数 10 表示下边距。
          第四个参数 10 表示左边距。
         */
        setMargin(statusLabel, new Insets(5, 10, 5, 10));
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
        this.encodingLabel.setText(getEncodingFormattedText(encoding) + "\t");
    }

    /**
     * 更新字数统计
     */
    public void updateWordCountStatusLabel() {
        CenterTabPane instance = CenterTabPane.getInstance();
        if (instance.getSelected() == null) {
            return;
        }
        LineNumberTextArea textArea = instance.getSelected().getLineNumberTextArea();
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
        CenterTabPane instance = CenterTabPane.getInstance();
        if (instance.getSelected() != null) {
            updateWordCountStatusLabel();
            CenterTab centerTab = instance.getSelected();
            if (centerTab != null) {
                updateEncodingLabel(centerTab.getCharset().name());

                // 添加光标位置变化监听器
                LineNumberTextArea textArea = centerTab.getLineNumberTextArea();
                textArea.caretPositionProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        updateRowColumnLabel(textArea.getCaretPosition(), textArea.getText());
                    }
                });
            }
        }
    }

    /**
     * 更新行列信息
     * @param caretPosition 光标位置
     * @param text 文本内容
     */
    private void updateRowColumnLabel(int caretPosition, String text) {
        int row = getRow(caretPosition, text);
        int column = getColumn(caretPosition, text);
        statusLabel.setText(getStatusBarFormattedText(row, column, text.length()));
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
        String encodingLabelFormat = "%s : %s";
        return String.format(encodingLabelFormat, UiResourceBundle.getContent(TextConstants.ENCODE), encoding);
    }
}
