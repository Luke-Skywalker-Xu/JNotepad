package org.jcnc.jnotepad.views.root.bottom.status;

import javafx.scene.control.Label;
import org.jcnc.jnotepad.ui.module.AbstractHorizontalBox;

/**
 * 状态栏组件封装。
 * 1. 文字统计
 * 2. 编码
 *
 * @author songdragon
 */
public class BottomStatusBox extends AbstractHorizontalBox {

    private static final BottomStatusBox INSTANCE = new BottomStatusBox();

    /**
     * 字数统计及光标
     */
    private final Label statusLabel = new Label();
    /**
     * 显示文本编码
     */
    private final Label encodingLabel = new Label();

    private BottomStatusBox() {

    }

    public static BottomStatusBox getInstance() {
        return INSTANCE;
    }


    public Label getStatusLabel() {
        return statusLabel;
    }

    public Label getEncodingLabel() {
        return encodingLabel;
    }
}
