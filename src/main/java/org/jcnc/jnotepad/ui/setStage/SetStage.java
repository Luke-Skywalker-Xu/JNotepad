package org.jcnc.jnotepad.ui.setStage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 设置窗口管理类，实现了单例模式。
 *
 * @author 许轲
 */
public class SetStage {

    // 唯一的 SetStage 实例，使用单例模式
    private static final SetStage SET_STAGE = new SetStage();
    private StackPane contentDisplay;

    private ListView<String> generalListView;
    private ListView<String> appearanceListView;
    private ListView<String> securityListView;

    /**
     * 获取 SetStage 的唯一实例。
     *
     * @return SetStage 的实例
     */
    public static SetStage getInstance() {
        return SET_STAGE;
    }

    private SetStage() {
        // 私有构造方法以实现单例模式
    }

    /**
     * 打开设置窗口。
     */
    public void openSetStage() {
        // 创建一个用于显示内容的 StackPane，并初始化为默认显示的标签
        contentDisplay = new StackPane(new Label("从左侧选择设置以显示内容"));

        // 创建不同设置类别的 ListView，并初始化设置项
        generalListView = createListView("常规设置项1", "常规设置项2");
        appearanceListView = createListView("外观设置项1", "外观设置项2");
        securityListView = createListView("安全设置项1", "安全设置项2");

        // 创建 TitledPanes，每个面板包含一个标题和对应的设置列表
        TitledPane generalPane = new TitledPane("常规设置", generalListView);
        TitledPane appearancePane = new TitledPane("外观设置", appearanceListView);
        TitledPane securityPane = new TitledPane("安全设置", securityListView);

        // 为 TitledPanes 添加点击事件监听器，用于更新内容显示区域
        generalPane.setOnMouseClicked(event -> updateContentDisplay(generalListView));
        appearancePane.setOnMouseClicked(event -> updateContentDisplay(appearanceListView));
        securityPane.setOnMouseClicked(event -> updateContentDisplay(securityListView));

        // 创建一个垂直布局容器，将 TitledPanes 放入其中
        VBox titledPaneContainer = new VBox(10);
        titledPaneContainer.getChildren().addAll(generalPane, appearancePane, securityPane);

        // 创建一个分割面板，左侧是设置列表，右侧是内容显示区域
        SplitPane splitPane = new SplitPane(titledPaneContainer, contentDisplay);
        splitPane.setDividerPositions(0.3); // 设置分割位置

        // 创建设置窗口的场景
        Scene scene = new Scene(splitPane, 800, 600);

        // 创建设置窗口的 Stage，并设置标题和场景
        Stage setStage = new Stage();
        setStage.setScene(scene);
        setStage.setTitle("设置窗口");
        setStage.show();
    }

    /**
     * 创建一个 ListView 并初始化项。
     *
     * @param items 要添加到 ListView 的项
     * @return 初始化后的 ListView
     */
    private ListView<String> createListView(String... items) {
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(items);
        return listView;
    }

    /**
     * 更新内容显示区域，根据选定的列表项显示相应的设置内容。
     *
     * @param listView 选定项所属的 ListView
     */
    private void updateContentDisplay(ListView<String> listView) {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // 更新内容显示区域为选定项的设置内容标签
            contentDisplay.getChildren().setAll(new Label(selectedItem + " 的设置内容"));
        }
    }



}
