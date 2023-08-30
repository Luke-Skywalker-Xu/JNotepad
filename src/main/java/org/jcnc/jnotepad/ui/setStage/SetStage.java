package org.jcnc.jnotepad.ui.setStage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcnc.jnotepad.tool.UiUtil;

/**
 * @author 许轲
 */
public class SetStage {

    private static final SetStage SET_STAGE = new SetStage();
    /**
     * Declare contentDisplay as a class member
     */
    private StackPane contentDisplay;

    public static SetStage getInstance() {
        return SET_STAGE;
    }

    private SetStage() {
        openSetStage(new Stage());
    }

    public void openSetStage(Stage setStage) {

        contentDisplay = new StackPane(new Label("从左侧选择设置以显示内容"));

        // 创建 TitledPane 组件
        TitledPane generalPane = new TitledPane("常规设置", createListView("常规设置项1", "常规设置项2"));
        TitledPane appearancePane = new TitledPane("外观设置", createListView("外观设置项1", "外观设置项2"));
        TitledPane securityPane = new TitledPane("安全设置", createListView("安全设置项1", "安全设置项2"));

        generalPane.setOnMouseClicked(event -> {
            ListView<String> listView = (ListView<String>) generalPane.getContent();
            if (listView.getSelectionModel().getSelectedItem() != null) {
                contentDisplay.getChildren().setAll(new Label(listView.getSelectionModel().getSelectedItem() + " 的设置内容"));
            }
        });

        appearancePane.setOnMouseClicked(event -> {
            ListView<String> listView = (ListView<String>) appearancePane.getContent();
            if (listView.getSelectionModel().getSelectedItem() != null) {
                contentDisplay.getChildren().setAll(new Label(listView.getSelectionModel().getSelectedItem() + " 的设置内容"));
            }
        });

        securityPane.setOnMouseClicked(event -> {
            ListView<String> listView = (ListView<String>) securityPane.getContent();
            if (listView.getSelectionModel().getSelectedItem() != null) {
                contentDisplay.getChildren().setAll(new Label(listView.getSelectionModel().getSelectedItem() + " 的设置内容"));
            }
        });

        // 创建一个 VBox 来容纳 TitledPanes
        VBox titledPaneContainer = new VBox(10);
        titledPaneContainer.getChildren().addAll(generalPane, appearancePane, securityPane);

        // 创建一个占位符，用于显示右侧内容区域
        StackPane stackPane = new StackPane(new Label("从左侧选择设置以显示内容"));

        // 设置 TitledPanes 的点击事件监听器
        generalPane.setOnMouseClicked(event -> stackPane.getChildren().setAll(new Label("常规设置内容")));
        appearancePane.setOnMouseClicked(event -> stackPane.getChildren().setAll(new Label("外观设置内容")));
        securityPane.setOnMouseClicked(event -> stackPane.getChildren().setAll(new Label("安全设置内容")));

        // 创建一个水平分割面板来容纳整个布局
        SplitPane splitPane = new SplitPane(titledPaneContainer, stackPane);
        // 设置分隔条位置
        splitPane.setDividerPositions(0.3);

        // 创建场景
        Scene scene = new Scene(splitPane, 800, 600);

        // 设置场景并显示窗口
        setStage.setScene(scene);
        setStage.setTitle("设置窗口");
        setStage.getIcons().add(UiUtil.getIcon());
        setStage.show();
    }

    private ListView<String> createListView(String... items) {
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(items);

        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                contentDisplay.getChildren().setAll(new Label(selectedItem + " 的设置内容"));
            }
        });
        return listView;
    }


}
