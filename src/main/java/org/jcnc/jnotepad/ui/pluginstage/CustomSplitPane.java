package org.jcnc.jnotepad.ui.pluginstage;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;

/**
 * 自定义分割面板，用于将两个组件以水平方向分割显示。
 * 
 * <p>该分割面板包含左侧和右侧两个区域，可分别设置内容。</p>
 * 
 * @author luke
 */
public class CustomSplitPane extends SplitPane {

    private final HBox leftBox;
    private final HBox rightBox;

    /**
     * 创建一个自定义分割面板，指定左侧和右侧的文本标签。
     * 
     * @param leftText  左侧区域的文本标签
     * @param rightText 右侧区域的文本标签
     */
    public CustomSplitPane(String leftText, String rightText) {
        // 创建左侧和右侧的框
        leftBox = createBox(leftText);
        rightBox = createBox(rightText);

        // 设置水平分割面板的属性
        this.setOrientation(Orientation.HORIZONTAL);
        this.setDividerPositions(0.5);

        // 添加左侧和右侧的框到分割面板
        this.getItems().addAll(leftBox, rightBox);
    }

    private HBox createBox(String text) {
        HBox box = new HBox();
        box.getChildren().add(new javafx.scene.control.Label(text));
        return box;
    }

    /**
     * 设置左侧区域的内容。
     * 
     * @param content 左侧区域的内容节点
     */
    public void setLeftContent(javafx.scene.Node content) {
        leftBox.getChildren().clear();
        leftBox.getChildren().add(content);
    }

    /**
     * 设置右侧区域的内容。
     * 
     * @param content 右侧区域的内容节点
     */
    public void setRightContent(javafx.scene.Node content) {
        rightBox.getChildren().clear();
        rightBox.getChildren().add(content);
    }
}
