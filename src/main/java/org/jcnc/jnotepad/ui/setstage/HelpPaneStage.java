package org.jcnc.jnotepad.ui.setstage;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.manager.RootManager;

import static org.jcnc.jnotepad.common.constants.AppConstants.*;

/**
 * 帮助页面
 *
 * @author gewuyou
 */
public class HelpPaneStage extends AbstractPaneStage {
    public HelpPaneStage() {
        initialize();
    }

    private static Button getButton(String text, EventHandler<ActionEvent> eventEventHandler) {
        Button button = new Button(text);
        button.getStyleClass().addAll(Styles.SMALL);
        button.setOnAction(eventEventHandler);
        return button;
    }

    /**
     * 获取舞台图标
     *
     * @return 舞台图标
     */
    @Override
    protected Image getStageIcon() {
        return UiUtil.getAppIcon();
    }

    /**
     * 获取舞台标题
     *
     * @return 舞台标题
     */
    @Override
    protected String getStageTitle() {
        return "关于 " + APP_NAME;
    }

    /**
     * 获取自定义舞台
     *
     * @return 舞台
     */
    @Override
    protected Scene getCustomizationScene() {
        return new Scene(this, 450, 240);
    }

    /**
     * 初始化方法
     */
    @Override
    protected void initialize() {
        initBottomBox();

        initIconBox();

        initTextBox();
    }

    private void initTextBox() {
        VBox textBox = new VBox();
        textBox.setPadding(new Insets(10));
        HBox titleBox = new HBox(5);
        titleBox.setPadding(new Insets(10, 0, 0, 0));

        Label titleLabel = new Label(APP_NAME);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label versionLabel = new Label(VERSION);
        versionLabel.setPadding(new Insets(0.5, 0, 0, 0));
        versionLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        titleBox.getChildren().addAll(titleLabel, versionLabel);

        Label descriptionLabel = getLabel(APP_NAME + "是一款自由的集成开发环境。", new Insets(8, 0, 8, 0));

        VBox linkBox = new VBox(7);
        Hyperlink repositoryLink = getHyperlink("仓库地址", "https://gitee.com/jcnc-org/JNotepad");
        Hyperlink feedBackLink = getHyperlink("提交反馈", "https://gitee.com/jcnc-org/JNotepad/issues/new/choose");
        Hyperlink qLink = getHyperlink("加入QQ群", "https://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=x3QF-jrJAKTiwu8kV5-giBk2ow66Kzyr&authKey=qNqrQauD7Ra4fXH%2Ftu4ylHXCyrf2EOYj9oMYOmFjlzYmrgDL8Yd0m2qhrQQEBL25&noverify=0&group_code=386279455");
        linkBox.getChildren().addAll(repositoryLink, feedBackLink, qLink);

        Label authorLabel = getLabel("Copyleft © 2023 " + AUTHOR + ".", new Insets(8, 0, 8, 0));
        textBox.getChildren().addAll(titleBox, descriptionLabel, linkBox, authorLabel);
        this.setCenter(textBox);
    }

    private void initIconBox() {
        VBox iconBox = new VBox();
        ImageView iconImageView = new ImageView(new Image("icon.png"));
        iconImageView.setFitWidth(50);
        iconImageView.setFitHeight(50);
        iconBox.setPadding(new Insets(20));
        iconBox.getChildren().addAll(iconImageView);
        this.setLeft(iconBox);
    }

    private void initBottomBox() {
        HBox bottomBox = new HBox(10);
        bottomBox.setPadding(new Insets(7, 15, 7, 0));

        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);

        Button leftBtn = getButton(" 复制并关闭 ", event -> {
            // 获取 RootManager 的实例
            RootManager rootManager = RootManager.getInstance();

            // 创建一个新的 Notification
            Notification notification = new Notification();
            notification.setMessage("已成功复制软件信息!");

            // 调用 RootManager 中的方法来显示 Notification
            rootManager.addNotificationToStackPane(rootManager.getRootStackPane(), notification);

            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            String info = "软件名字:" + APP_NAME + "\t" + "版本:" + VERSION;
            content.putString(info);
            LogUtil.getLogger(this.getClass()).info("软件信息已经复制到剪贴板:{}", info);
            clipboard.setContent(content);
            // 关闭当前的 Stage
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        });
        Button rightBtn = getButton(" 关闭 ", event -> {
            // 关闭当前的 Stage
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();
        });
        bottomBox.getChildren().addAll(leftBtn, rightBtn);
        this.setBottom(bottomBox);
    }

    private Label getLabel(String text, Insets insets) {
        Label label = new Label(text);
        label.setPadding(insets);
        label.setStyle("-fx-font-size: 14px;");
        return label;
    }

    /**
     * 获取超链接
     *
     * @param text 链接文本
     * @param url  链接
     * @return 超链接
     */
    private Hyperlink getHyperlink(String text, String url) {
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setText(text);
        hyperlink.setOnAction(event -> openWebsite(url));
        hyperlink.setVisited(true);
        hyperlink.setMnemonicParsing(true);
        hyperlink.setStyle("-color-link-fg-visited:-color-accent-fg;");
        return hyperlink;
    }

    /**
     * 自定义启动方法
     *
     * @param stage 舞台
     */
    @Override
    public void run(Stage stage) {
        stage.getIcons().add(getStageIcon());
        stage.setTitle(getStageTitle());
        stage.setScene(getCustomizationScene());
        stage.setResizable(false);
        stage.show();
    }

    /**
     * 打开网页方法
     */
    private void openWebsite(String url) {
        try {
            LogUtil.getLogger(this.getClass()).info("正在打开---{}", url);
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            LogUtil.getLogger(this.getClass()).info("打开失败---{}", url);
        }
    }
}
