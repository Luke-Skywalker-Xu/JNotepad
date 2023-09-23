package org.jcnc.jnotepad.ui.pluginstage;

import atlantafx.base.controls.Tile;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.Styles;
import javafx.geometry.Insets;
import javafx.scene.Node;

import java.awt.Desktop;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jcnc.jnotepad.util.LogUtil;
import org.slf4j.Logger;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 插件管理面板，用于管理插件的市场、已安装和设置功能。
 *
 * <p>此面板包含一个自定义分割面板，用于显示不同的插件管理选项卡内容。</p>
 *
 * @author luke
 */
public class PluginManagementPane extends BorderPane {

    public static int ICON_SIZE = 40;
    Logger logger = LogUtil.getLogger(this.getClass());


    private CustomSplitPane customSplitPane;
    private final Map<Tile, Node> tileContentMap = new HashMap<>();

    /**
     * 创建一个插件管理面板的实例。
     */
    public PluginManagementPane() {
        init();
    }

    /**
     * 初始化插件管理面板。
     */
    private void init() {
        // 创建选项卡面板
        TabPane tabPane = new TabPane();

        // 创建市场、已安装和设置选项卡
        Tab marketTab = new Tab("发现");
        Tab installedTab = new Tab("已安装");
        Tab myTab = new Tab("设置");

        // 禁用选项卡关闭按钮
        marketTab.setClosable(false);
        installedTab.setClosable(false);
        myTab.setClosable(false);

        // 创建选项卡内容面板
        BorderPane marketTabContent = new BorderPane();
        BorderPane installedTabContent = new BorderPane();
        BorderPane myTabContent = new BorderPane();

        // 创建自定义分割面板
        customSplitPane = new CustomSplitPane("", "");
        marketTabContent.setCenter(customSplitPane);

        // 获取插件列表
        customSplitPane.setLeftContent(getScrollPane());

        // 创建示例按钮并添加到已安装和设置选项卡中
        installedTabContent.setCenter(new Button("2"));
        myTabContent.setCenter(new Button("3"));

        // 将选项卡内容设置到选项卡中
        marketTab.setContent(marketTabContent);
        installedTab.setContent(installedTabContent);
        myTab.setContent(myTabContent);

        // 将选项卡添加到选项卡面板中
        tabPane.getTabs().addAll(marketTab, installedTab, myTab);

        // 将选项卡面板设置为插件管理面板的中心内容
        this.setCenter(tabPane);
    }

    /**
     * 创建包含插件列表的VBox，并将其包装在滚动面板中。
     *
     * @return 包含插件列表的滚动面板
     */
    private ScrollPane getScrollPane() {
        // 创建示例插件列表项
        var image1 = new Image("plug.png");
        var tile1 = createTile("运行插件", "这是一个运行插件\t\t\t\t\t\t", image1);

        var image2 = new Image("plug.png");
        var tile2 = createTile("终端插件", "这是一个终端插件", image2);

        var image3 = new Image("plug.png");
        var tile3 = createTile("构建插件", "这是一个构建插件", image3);

        var image4 = new Image("plug.png");
        var tile4 = createTile("1", "这是一个构建插件", image4);

        var image5 = new Image("plug.png");
        var tile5 = createTile("2", "这是一个构建插件", image5);

        var image6 = new Image("plug.png");
        var tile6 = createTile("4", "这是一个构建插件", image6);

        var image7 = new Image("plug.png");
        var tile7 = createTile("5", "这是一个构建插件", image7);

        // 创建VBox并将插件列表项添加到其中
        var box = new VBox(tile1, tile2, tile3, tile4, tile5, tile6, tile7);

        // 创建滚动面板并将VBox设置为其内容
        var scrollPane = new ScrollPane(box);

        // 设置滚动面板的宽度适应父容器
        scrollPane.setFitToWidth(true);
        // 隐藏滚动条
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        return scrollPane;
    }

    /**
     * 创建插件列表项Tile。
     *
     * @param title       插件标题
     * @param description 插件描述
     * @param image       插件图标
     * @return 创建的插件列表项Tile
     */
    private Tile createTile(String title, String description, Image image) {
        // 创建一个title
        var tile = new Tile(title, description);
        // 创建一个按钮
        var tgl = new ToggleSwitch();

        // 创建一个图标
        ImageView icon = new ImageView(image);
        // 指定要缩放的固定像素大小
        double iconSize = ICON_SIZE;

        // 设置图像视图的宽度和高度，以便等比例缩放到指定像素大小
        icon.setFitWidth(iconSize);
        icon.setFitHeight(iconSize);

        // 设置Tile的图标
        tile.setGraphic(icon);

        // 设置Tile的操作和操作处理程序
        tile.setAction(tgl);
        tile.setActionHandler(() -> {
            customSplitPane.setRightContent(tileContentMap.get(tile));
            logger.info("点击了" + tile);
        });

        // 创建专属的customSplitPane内容
        var content = createCustomSplitPaneContent(title);

        // 将内容与Tile关联起来
        tileContentMap.put(tile, content);

        return tile;
    }


    /**
     * 创建专属于每个插件的CustomSplitPane内容。
     *
     * @param titleName 插件标题
     * @return 创建的CustomSplitPane内容
     */
    private Node createCustomSplitPaneContent(String titleName) {
        VBox content = new VBox(8);
        content.setPadding(new Insets(10));
        var titleLabel = new Text(titleName);
        titleLabel.getStyleClass().addAll(Styles.TITLE_1);

        var authorBox = new HBox(10);
        var author = new Text("JCNC团队");
        var authorLink = new Hyperlink("插件仓库地址");
        authorLink.setVisited(true);
        authorLink.setStyle("-fx-text-fill: blue; -fx-visited-link-color: blue;");
        authorLink.setOnAction(event -> {
            // 定义要打开的链接
            String url = "https://gitee.com/jcnc-org/JNotepad";

            try {
                // 创建URI对象
                URI uri = new URI(url);

                // 检查系统是否支持Desktop类
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();

                    // 检查是否支持浏览器启动
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        // 打开默认浏览器并访问链接
                        desktop.browse(uri);
                    } else {
                        logger.info("系统不支持浏览器启动操作!");
                    }
                } else {
                    logger.info("系统不支持Desktop类!");
                }
            } catch (Exception e) {
                logger.info("启动" + authorLink + "失败!");
            }
        });

        authorBox.getChildren().addAll(author, authorLink);



        var descriptionLabel = new Text("插件描述插件描述插件描述");


        content.getChildren().addAll(titleLabel, authorBox, descriptionLabel);

        return content;
    }
}
