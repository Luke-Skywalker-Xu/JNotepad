package org.jcnc.jnotepad.ui.setStage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetStage {

    private static final SetStage SET_STAGE = new SetStage();
    private StackPane contentDisplay;

    public static SetStage getInstance() {
        return SET_STAGE;
    }

    private SetStage() {
        // Private constructor to enforce Singleton pattern
    }

    public void openSetStage() {
        contentDisplay = new StackPane(new Label("从左侧选择设置以显示内容"));

        TitledPane generalPane = new TitledPane("常规设置", createListView("常规设置项1", "常规设置项2"));
        TitledPane appearancePane = new TitledPane("外观设置", createListView("外观设置项1", "外观设置项2"));
        TitledPane securityPane = new TitledPane("安全设置", createListView("安全设置项1", "安全设置项2"));

        // 设置 TitledPanes 的点击事件监听器
        generalPane.setOnMouseClicked(event -> updateContentDisplay((ListView<String>) generalPane.getContent()));
        appearancePane.setOnMouseClicked(event -> updateContentDisplay((ListView<String>) appearancePane.getContent()));
        securityPane.setOnMouseClicked(event -> updateContentDisplay((ListView<String>) securityPane.getContent()));

        VBox titledPaneContainer = new VBox(10);
        titledPaneContainer.getChildren().addAll(generalPane, appearancePane, securityPane);

        SplitPane splitPane = new SplitPane(titledPaneContainer, contentDisplay);
        splitPane.setDividerPositions(0.3);

        Scene scene = new Scene(splitPane, 800, 600);

        Stage setStage = new Stage();
        setStage.setScene(scene);
        setStage.setTitle("设置窗口");
        setStage.show();
    }

    private ListView<String> createListView(String... items) {
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(items);

        listView.setOnMouseClicked(event -> updateContentDisplay(listView));

        return listView;
    }

    private void updateContentDisplay(ListView<String> listView) {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            contentDisplay.getChildren().setAll(new Label(selectedItem + " 的设置内容"));
        }
    }
}