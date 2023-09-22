package org.jcnc.jnotepad.ui.pluginstage;

import atlantafx.base.controls.Tile;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
        // 设置用户代理样式表
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        // 创建选项卡面板
        TabPane tabPane = new TabPane();

        // 创建市场、已安装和设置选项卡
        Tab marketTab = new Tab("市场");
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
        var box = getBox();
        customSplitPane.setLeftContent(box);

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
     * 创建包含插件列表的VBox。
     *
     * @return 包含插件列表的VBox
     */
    private VBox getBox() {
        // 创建示例插件列表项
        var tile1 = createTile("运行插件", "这是一个运行插件\t\t\t\t\t\t\t    ");
        var tile2 = createTile("终端插件", "这是一个终端插件");
        var tile3 = createTile("构建插件", "这是一个构建插件");

        // 创建VBox并将插件列表项添加到其中
        var box = new VBox(tile1, tile2, tile3);

        return box;
    }

    /**
     * 创建插件列表项Tile。
     *
     * @param title       插件标题
     * @param description 插件描述
     * @return 创建的插件列表项Tile
     */
    private Tile createTile(String title, String description) {
        var tile = new Tile(title, description);
        var tgl = new ToggleSwitch();

        // 设置Tile的操作和操作处理程序
        tile.setAction(tgl);
        tile.setActionHandler(() -> {
            customSplitPane.setRightContent(tileContentMap.get(tile));
            System.out.println("点击了" + title);
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
     * @param title 插件标题
     * @return 创建的CustomSplitPane内容
     */
    private Node createCustomSplitPaneContent(String title) {

        // TODO: 2023/9/23 未完成
        return new Label("详情" + title);
    }
}
