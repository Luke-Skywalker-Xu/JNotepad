package org.jcnc.jnotepad.ui.component.stage.topmenu.plugin;

import atlantafx.base.controls.Tile;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jcnc.jnotepad.api.core.component.stage.AbstractPaneStage;
import org.jcnc.jnotepad.app.utils.LoggerUtil;
import org.jcnc.jnotepad.app.utils.PopUpUtil;
import org.jcnc.jnotepad.app.utils.UiUtil;
import org.jcnc.jnotepad.controller.plugin.manager.PluginManager;
import org.jcnc.jnotepad.model.entity.PluginDescriptor;
import org.jcnc.jnotepad.ui.component.stage.setting.CustomSetButton;
import org.slf4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 插件管理面板，用于管理插件的市场、已安装和设置功能。
 *
 * <p>此面板包含一个自定义分割面板，用于显示不同的插件管理选项卡内容。</p>
 *
 * @author luke
 */
public class PluginManagementPane extends AbstractPaneStage {
    /**
     * 图标大小常量
     */
    public static int ICON_SIZE = 40;
    /**
     * 用于存储Tile与其内容节点的映射关系
     */
    private final Map<Tile, Node> tileContentMap = new HashMap<>();
    PluginManager pluginManager = PluginManager.getInstance();
    /**
     * 日志记录器
     */
    Logger logger = LoggerUtil.getLogger(this.getClass());
    /**
     * 自定义分割面板
     */
    private CustomSplitPane customSplitPane;

    /**
     * 创建一个插件管理面板的实例。
     */
    public PluginManagementPane() {
        initialize();
    }

    /**
     * 初始化插件管理面板。
     */
    @Override
    protected void initialize() {
        // 初始化插件临时集合
        pluginManager.initializeTemporaryPluginDescriptors();
        // 创建选项卡面板
        TabPane rootTabPane = new TabPane();

        // 创建市场、已安装和设置选项卡
        Tab installedTab = new Tab("已安装");
        Tab marketTab = new Tab("市场");
        Tab myTab = new Tab("设置");

        // 禁用选项卡关闭按钮
        installedTab.setClosable(false);
        marketTab.setClosable(false);
        myTab.setClosable(false);

        // 创建选项卡内容面板
        BorderPane marketTabContent = new BorderPane();
        BorderPane installedTabContent = new BorderPane();
        BorderPane myTabContent = new BorderPane();

        // 创建自定义分割面板
        customSplitPane = new CustomSplitPane("", "");
        installedTabContent.setCenter(customSplitPane);

        // 获取插件列表
        customSplitPane.setLeftContent(getScrollPane());

        // 创建示例按钮并添加到已安装和设置选项卡中
        marketTabContent.setCenter(new Button("市场"));

        var myTabPane = new BorderPane();
        var mainMyTabPane = new VBox();
        var manageStorage = new Button("管理插件仓库");
        manageStorage.setOnAction(event -> {
            try {
                // 获取当前软件运行根目录
                String rootPath = System.getProperty("user.dir");
                File rootDir = new File(rootPath);

                // 打开文件资源管理器并选中运行根目录
                Desktop desktop = Desktop.getDesktop();
                desktop.open(rootDir);
            } catch (Exception e) {
                logger.info("打开了" + System.getProperty("user.dir") + "文件夹");

            }
        });
        mainMyTabPane.getChildren().addAll(manageStorage);
        myTabPane.setCenter(mainMyTabPane);
        myTabContent.setCenter(myTabPane);


        // 将选项卡内容设置到选项卡中
        installedTab.setContent(installedTabContent);
        marketTab.setContent(marketTabContent);
        myTab.setContent(myTabContent);

        // 将选项卡添加到选项卡面板中
        rootTabPane.getTabs().addAll(marketTab, installedTab, myTab);

        // 将选项卡面板设置为插件管理面板的中心内容
        this.setCenter(rootTabPane);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setStyle("-fx-background-color: rgba(43,43,43,0.12);");
        bottomBox.setPadding(new Insets(7, 15, 7, 0));
        Button applicationButton = new Button(" 应用 ");
        applicationButton.getStyleClass().addAll(Styles.SMALL);
        applicationButton.setOnAction(event -> pluginManager.saveNotExitSettings());

        Button confirmButton = new Button(" 确认 ");
        confirmButton.setTextFill(Color.WHITE);
        confirmButton.getStyleClass().addAll(Styles.SMALL);
        confirmButton.setStyle("-fx-background-color: rgb(54,88,128);");
        confirmButton.setOnAction(event -> {
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            // 保存设置
            pluginManager.saveAndExitSettings();
            stage.close();
        });

        CustomSetButton cancelButton = new CustomSetButton(" 取消 ");
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            // 清空临时集合
            pluginManager.clearTemporarySettings();
            stage.close();
        });
        cancelButton.getStyleClass().addAll(Styles.SMALL);

        bottomBox.getChildren().addAll(confirmButton, cancelButton, applicationButton);

        this.setBottom(bottomBox);
    }

    /**
     * 自定义启动方法
     *
     * @param stage 舞台
     */
    @Override
    public void run(Stage stage) {
        // 在此添加自定义逻辑
        stage.show();
    }

    /**
     * 创建包含插件列表的VBox，并将其包装在滚动面板中。
     *
     * @return 包含插件列表的滚动面板
     */
    private ScrollPane getScrollPane() {
        List<Tile> tiles = new ArrayList<>();
        // 将拷贝的插件信息类添加进列表
        pluginManager.getTemporaryPluginDescriptors().forEach(pluginDescriptor -> tiles.add(createPlugInListItem(pluginDescriptor)));

        // 创建VBox并将插件列表项添加到其中
        var box = new VBox();
        box.getChildren().addAll(tiles);

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
     * @return 创建的插件列表项Tile
     */
    private Tile createPlugInListItem(PluginDescriptor pluginDescriptor) {
        // 创建一个title
        var tile = new Tile(pluginDescriptor.getName(), pluginDescriptor.getDescription());
        // 创建一个按钮
        var toggleSwitch = new ToggleSwitch();
        // 创建一个图标
        ImageView icon = new ImageView(
                new Image(
                        pluginDescriptor.getIcon() == null ||
                                pluginDescriptor.getIcon().isEmpty() ? "plug.png" : pluginDescriptor.getIcon()));
        // 指定要缩放的固定像素大小
        double iconSize = ICON_SIZE;

        // 设置图像视图的宽度和高度，以便等比例缩放到指定像素大小
        icon.setFitWidth(iconSize);
        icon.setFitHeight(iconSize);

        // 设置Tile的图标
        tile.setGraphic(icon);

        // 设置Tile的操作和操作处理程序
        tile.setAction(toggleSwitch);
        tile.setActionHandler(() -> {
            customSplitPane.setRightContent(tileContentMap.get(tile));
            logger.info("点击了{}", tile);
        });

        // 创建专属的customSplitPane内容
        var content = createCustomSplitPaneContent(pluginDescriptor, toggleSwitch, tile);

        // 将内容与Tile关联起来
        tileContentMap.put(tile, content);

        return tile;
    }

    /**
     * 创建专属于每个插件的CustomSplitPane内容。
     *
     * @return 创建的CustomSplitPane内容
     */
    private Node createCustomSplitPaneContent(PluginDescriptor pluginDescriptor, ToggleSwitch toggleSwitch, Tile tile) {
        VBox content = new VBox(8);
        content.setPadding(new Insets(10));
        var titleLabel = new Text(pluginDescriptor.getName());
        titleLabel.getStyleClass().addAll(Styles.TITLE_1);

        var authorBox = new HBox(10);
        var author = new Text(pluginDescriptor.getAuthor());
        var authorLink = getAuthorLink();
        authorBox.getChildren().addAll(author, authorLink);


        var uninstallItem = new MenuItem("卸载");

        var state = new SplitMenuButton(uninstallItem);
        toggleSwitch.setSelected(pluginDescriptor.isEnabled());
        BooleanProperty booleanProperty = toggleSwitch.selectedProperty();
        state.textProperty().bind(Bindings.when(booleanProperty).then("禁用").otherwise("启用"));

        uninstallItem.setOnAction(event -> PopUpUtil.warningAlert("卸载", "确定要卸载" + pluginDescriptor.getName() + "吗?", "此操作无法撤销!", dialog -> {
            pluginManager.unloadPlugin(pluginDescriptor);
            state.setDisable(true);
            toggleSwitch.setDisable(true);
            dialog.close();
        }, null));
        state.getStyleClass().addAll(Styles.ACCENT);
        state.setPrefWidth(80);
        toggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                pluginManager.enablePlugIn(pluginDescriptor);
                customSplitPane.setRightContent(tileContentMap.get(tile));
            } else {
                pluginManager.disablePlugIn(pluginDescriptor);
                customSplitPane.setRightContent(tileContentMap.get(tile));
            }
        });
        state.setOnAction(event -> {
            toggleSwitch.setSelected(!toggleSwitch.isSelected());
            if (toggleSwitch.isSelected()) {
                pluginManager.enablePlugIn(pluginDescriptor);
            } else {
                pluginManager.disablePlugIn(pluginDescriptor);
            }

        });
        var main = new VBox(10);

        // 创建TabPane并添加标签页
        TabPane tabPane = new TabPane();

        Tab detailsTab = new Tab("简介");
        detailsTab.setClosable(false);
        Tab featuresTab = new Tab("详细信息");
        featuresTab.setClosable(false);
        Tab changelogTab = new Tab("更新日志");
        changelogTab.setClosable(false);
        Tab readMeTab = new Tab("README");
        readMeTab.setClosable(false);
        Tab othersTab = new Tab("其它信息");
        othersTab.setClosable(false);
        // 在标签页中添加内容
        VBox detailsContent = new VBox(10);

        // 创建一个WebView来显示Markdown内容
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        // 从外部文件加载Markdown内容
        String markdownContent = loadMarkdownFromFile("README.md");
        String htmlContent = markdownToHtml(markdownContent);

        // 加载HTML内容到WebView
        engine.loadContent(htmlContent);
        // 将WebView添加到detailsContent
        detailsContent.getChildren().addAll(webView);
        VBox featuresContent = new VBox(10);
        VBox changelogContent = new VBox(10);
        VBox readMeContent = new VBox(10);
        detailsTab.setContent(detailsContent);
        featuresTab.setContent(featuresContent);
        changelogTab.setContent(changelogContent);
        readMeTab.setContent(readMeContent);
        tabPane.getTabs().addAll(detailsTab, featuresTab, changelogTab, readMeTab, othersTab);

        main.getChildren().addAll(tabPane);

        content.getChildren().addAll(titleLabel, authorBox, state, main);

        // 将内容包装在滚动面板中
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    /**
     * 将Markdown内容转换为HTML格式。
     *
     * @param markdownContent Markdown格式的内容
     * @return HTML格式的内容
     */
    private String markdownToHtml(String markdownContent) {
        // 创建Markdown解析器
        Parser parser = Parser.builder().build();

        // 解析Markdown内容

        org.commonmark.node.Node document = parser.parse(markdownContent);

        // 创建HTML渲染器
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        // 将Markdown文档渲染为HTML
        return renderer.render(document);
    }

    /**
     * 从文件加载Markdown内容。
     *
     * @param filePath 文件路径
     * @return 加载的Markdown内容
     */
    private String loadMarkdownFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            logger.info("正在运行" + "loadMarkdownFromFile");
            return "";
        }
    }

    /**
     * 获取作者链接。
     *
     * @return 作者链接
     */
    private Hyperlink getAuthorLink() {
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
                logger.error("启动{}失败!\n错误是{}", authorLink, e.getMessage());
            }
        });
        return authorLink;
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
        return "插件管理";
    }

    /**
     * 获取舞台
     *
     * @return 舞台
     */
    @Override
    protected Scene getCustomizationScene() {
        return new Scene(this, 900, 600);
    }
}
