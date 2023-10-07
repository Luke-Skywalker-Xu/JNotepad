package org.jcnc.jnotepad.views.manager;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.component.module.TextCodeArea;
import org.jcnc.jnotepad.views.root.bottom.status.BottomStatusBox;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;

import java.nio.charset.Charset;

/**
 * 状态栏组件管理类
 *
 * @author gewuyou
 */
public class BottomStatusBoxManager {
    private static final BottomStatusBoxManager INSTANCE = new BottomStatusBoxManager();

    private static final BottomStatusBox BOTTOM_STATUS_BOX = BottomStatusBox.getInstance();

    private static final String STATUS_LABEL_FORMAT = "%s : %d \t%s: %d \t%s: %d \t";

    private String style = "-fx-background-color: rgba(43,43,43,0.12);";

    public static BottomStatusBoxManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化状态栏
     */
    public void initStatusBox() {
        BOTTOM_STATUS_BOX.setStyle(style);
        BOTTOM_STATUS_BOX.getChildren().clear();
        registerBottomStatusBox();
        updateEncodingLabel();
        updateWhenTabSelected();
        BOTTOM_STATUS_BOX.getProperties().put("borderpane-margin", new Insets(5, 10, 5, 10));
        BOTTOM_STATUS_BOX.setAlignment(Pos.BASELINE_RIGHT);
        UiResourceBundle.getInstance().addListener((observable, oldValue, newValue) -> updateWhenTabSelected());

        /*
          第一个参数 10 表示上边距。
          第二个参数 10 表示右边距。
          第三个参数 10 表示下边距。
          第四个参数 10 表示左边距。
         */
        HBox.setMargin(BOTTOM_STATUS_BOX.getStatusLabel(), new Insets(5, 10, 5, 10));
    }

    /**
     * 注册下方状态栏
     */
    public void registerBottomStatusBox() {
        Label statusLabel = BOTTOM_STATUS_BOX.getStatusLabel();
        registerChildrenByLabel(statusLabel);
        statusLabel.setText(getStatusBarFormattedText(0, 0, 1));

        registerChildrenByLabel(BOTTOM_STATUS_BOX.getEncodingLabel());
    }

    /**
     * 设置状态栏样式
     *
     * @param style 样式字符串
     */
    public void setBottomStatusBoxStyle(String style) {
        this.style = style;
    }

    /**
     * 注册状态栏标签组件
     *
     * @param label 标签组件
     */
    public void registerChildrenByLabel(Label label) {
        BOTTOM_STATUS_BOX.getChildren().add(label);
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
        BOTTOM_STATUS_BOX.getEncodingLabel().setText(getEncodingFormattedText(encoding) + "\t");
    }

    /**
     * 更新字数统计
     */
    public void updateWordCountStatusLabel() {
        CenterTabPaneManager instance = CenterTabPaneManager.getInstance();
        if (instance.getSelected() == null) {
            return;
        }
        TextCodeArea textArea = instance.getSelected().getTextCodeArea();
        int caretPosition = textArea.getCaretPosition();
        int row = getRow(caretPosition, textArea.getText());
        int column = getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        BOTTOM_STATUS_BOX.getStatusLabel().setText(getStatusBarFormattedText(row, column, length));
    }

    /**
     * Tab选中时，更新状态栏
     * <br>1. 状态栏更新当前选中tab的数字统计
     * <br>2. 状态栏更新当前选中tab的字符编码
     */
    public void updateWhenTabSelected() {
        CenterTabPaneManager instance = CenterTabPaneManager.getInstance();
        if (instance.getSelected() != null) {
            updateWordCountStatusLabel();
            CenterTab centerTab = instance.getSelected();
            if (centerTab != null) {
                updateEncodingLabel(centerTab.getCharset().name());

                // 添加光标位置变化监听器
                TextCodeArea textArea = centerTab.getTextCodeArea();
                textArea.caretPositionProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> updateRowColumnLabel(textArea.getCaretPosition(), textArea.getText()));
            }
        }
    }

    /**
     * 更新行列信息
     *
     * @param caretPosition 光标位置
     * @param text          文本内容
     */
    public void updateRowColumnLabel(int caretPosition, String text) {
        int row = getRow(caretPosition, text);
        int column = getColumn(caretPosition, text);
        BOTTOM_STATUS_BOX.getStatusLabel().setText(getStatusBarFormattedText(row, column, text.length()));
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

    public String getStatusBarFormattedText(int row, int column, int wordCount) {
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

    public String getEncodingFormattedText(String encoding) {
        String encodingLabelFormat = "%s : %s";
        return String.format(encodingLabelFormat, UiResourceBundle.getContent(TextConstants.ENCODE), encoding);
    }
}
