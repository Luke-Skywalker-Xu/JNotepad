package org.jcnc.jnotepad.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jcnc.jnotepad.constants.Constants;
import org.jcnc.jnotepad.controller.manager.Controller;
import org.jcnc.jnotepad.utils.StrUtil;

import static org.jcnc.jnotepad.view.manager.ViewManager.statusLabel;

/**
 * @Description
 * @Author lixinpiao
 * @Date 2023/8/12 14:19
 **/
public class JTab extends Tab {

    /**
     * 行号区域
     */
    private VBox lineNum;

    /**
     * 文本区域
     */
    private TextArea textArea;


    private JTab() {
        super();
    }

    public JTab(String s) {
        super(s);
    }

    public JTab(String s, TextArea node) {
        super(s, node);
        this.init(node);
    }

    public void init(TextArea textArea){

        //布局 Tab-> ScrollPane-> HBox-> (VBox->label[])+TextArea
        //左侧行号区
        VBox vBox = new VBox();
        vBox.setPrefWidth(40);
        vBox.setBorder(Border.EMPTY);
        this.setLineNum(vBox);

        //文本区
        textArea.setPrefWidth(Constants.SCREEN_WIDTH-60);
        textArea.setScrollLeft(0);
        textArea.setScrollTop(Double.MAX_VALUE);
        this.setTextArea(textArea);
        HBox hBox = new HBox();
        //行号+文本用容器HBox布局
        hBox.getChildren().addAll(this.getLineNum(),this.getTextArea());

        //滚动条 用容器ScrollPane布局
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(hBox);

        //设置文本框随着滚动条的变化而变化
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = (double) newValue;
            this.getTextArea().setPrefWidth(width-60);
        });
        scrollPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = (double) newValue;
            this.getTextArea().setPrefHeight(height+100);
        });

        //处理文本框内部的滚动条问题，目前不完善，todo 待优化
        hBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = (double) newValue;
            this.getTextArea().setPrefHeight(height+10);
        });

        this.setContent(scrollPane);
        new Controller().configureTextArea(textArea);

        updateRowAndColumn();

        textArea.textProperty().addListener((observable, oldValue, newValue) ->{
            updateRowAndColumn();
        });
    }



    /**
     * 更新行列状态栏
     */
    public void  updateRowAndColumn(){
        Controller controller=  new Controller();

        int allRow = this.getAllRaw();
        Label[] labels = new Label[allRow];
        for(int i=0;i<allRow;i++){
            labels[i] = new Label(String.valueOf(i+1));
            labels[i].setPrefWidth(40);
            labels[i].setAlignment(Pos.CENTER);
        }
        labels[0].setStyle("-fx-label-padding: 5px 0 0 0");
        this.getLineNum().getChildren().setAll(labels);

        int caretPosition = this.getTextArea().getCaretPosition();
        //更新左侧行号
        int row = controller.getRow(caretPosition, this.getTextArea().getText());
        //更新底部行和列
        int column = controller.getColumn(caretPosition, textArea.getText());
        int length = textArea.getLength();
        statusLabel.setText("行: " + row + " \t列: " + column + " \t字数: " + length);

    }

    private int getAllRaw(){

        int raw = StrUtil.countCharacters(this.getTextArea().getText(),'\n');
        return raw+1;
    }

    public VBox getLineNum() {
        return lineNum;
    }

    public void setLineNum(VBox lineNum) {
        this.lineNum = lineNum;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }
}
