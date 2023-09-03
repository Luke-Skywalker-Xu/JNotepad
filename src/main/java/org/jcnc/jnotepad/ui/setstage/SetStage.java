package org.jcnc.jnotepad.ui.setstage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * SetStage类表示设置窗口的单例对象。此窗口用于显示不同的设置选项和其对应的布局。
 * 通过调用getInstance方法获取SetStage的实例，并使用openSetStage方法打开设置窗口。
 */
public class SetStage extends Stage {

    private static SetStage instance;
    private StackPane contentDisplay;

    /**
     * 私有构造方法以实现单例模式。
     */
    private SetStage() {
    }

    /**
     * 获取SetStage的唯一实例。
     *
     * @return SetStage的实例
     */
    public static SetStage getInstance() {
        if (instance == null) {
            instance = new SetStage();
        }
        return instance;
    }

    /**
     * 打开设置窗口，显示不同的设置选项和对应的布局。
     */
    public void openSetStage() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("设置窗口");



        contentDisplay = new StackPane();

        TreeView<String> treeView = createTreeView();

        SplitPane splitPane = new SplitPane(treeView, contentDisplay);
        splitPane.setDividerPositions(0.3);

        HBox bottomBox= new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setStyle("-fx-background-color: rgba(43,43,43,0.12);");
        bottomBox.setPadding(new Insets(10,10,10,10));
        Button confirmButton=new Button(" 确认 ");
        Button cancelButton =new Button(" 取消 ");
        Button applicationButton =new Button(" 应用 ");
        bottomBox.getChildren().addAll(confirmButton,cancelButton,applicationButton);


        BorderPane root = new BorderPane();
        root.setCenter(splitPane);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建TreeView控件并设置其根节点，包括设置项的层次结构。
     *
     * @return 创建的TreeView对象
     */
    private TreeView<String> createTreeView() {
        TreeItem<String> root = new TreeItem<>("root");
        root.setExpanded(true);

        //常规设置树
        TreeItem<String> generalItem = new TreeItem<>("常规设置");

        TreeItem<String> generalItem1 = new TreeItem<>("常规设置项1");
        TreeItem<String> generalItem2 = new TreeItem<>("常规设置项2");
        generalItem.getChildren().add(generalItem1);
        generalItem.getChildren().add(generalItem2);


        //外观设置树
        TreeItem<String> appearanceItem = new TreeItem<>("外观设置");

        TreeItem<String> appearanceItem1 = new TreeItem<>("外观设置项1");
        TreeItem<String> appearanceItem2 = new TreeItem<>("外观设置项2");
        appearanceItem.getChildren().add(appearanceItem1);
        appearanceItem.getChildren().add(appearanceItem2);

        //安全设置树
        TreeItem<String> securityItem = new TreeItem<>("安全设置");

        TreeItem<String> securityItem1 = new TreeItem<>("安全设置项1");
        TreeItem<String> securityItem2 = new TreeItem<>("安全设置项2");
        securityItem.getChildren().add(securityItem1);
        securityItem.getChildren().add(securityItem2);

        root.getChildren().add(generalItem);
        root.getChildren().add(appearanceItem);
        root.getChildren().add(securityItem);
        TreeView<String> treeView = new TreeView<>(root);
        treeView.setShowRoot(false);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedItem = newValue.getValue();
                Node selectedLayout = createLayoutForSelectedItem(selectedItem);
                contentDisplay.getChildren().setAll(selectedLayout);
            }
        });

        return treeView;
    }
    private Node createLayoutForSelectedItem(String selectedItem) {
        return switch (selectedItem) {
            case "常规设置项1" -> createGeneralSettingsLayout1();
            case "常规设置项2" -> createGeneralSettingsLayout2();
            case "外观设置项1" -> createAppearanceSettingsLayout1();
            case "外观设置项2" -> createAppearanceSettingsLayout2();
            case "安全设置项1" -> createSecuritySettingsLayout1();
            case "安全设置项2" -> createSecuritySettingsLayout2();
            default -> null;
        };
    }

    /**
     * 创建常规设置项1的布局。
     *
     * @return 常规设置项1的布局节点
     */
    private Node createGeneralSettingsLayout1() {
        VBox generalLayout = new VBox();

        // 添加一个Label作为设置项的标题
        Label titleLabel = new Label("常规设置项1");

        // 添加一个TextField用于输入
        TextField textField = new TextField();
        textField.setPromptText("输入设置项1的值");

        // 添加一个CheckBox用于开关
        CheckBox checkBox = new CheckBox("启用设置项1");

        // 添加一个Button用于保存设置
        Button saveButton = new Button("保存设置");

        // 将所有节点添加到VBox布局中
        generalLayout.getChildren().addAll(titleLabel, textField, checkBox, saveButton);

        return generalLayout;
    }


    /**
     * 创建常规设置项2的布局。
     *
     * @return 常规设置项2的布局节点
     */
    private Node createGeneralSettingsLayout2() {
        VBox generalLayout = new VBox();
        generalLayout.getChildren().add(new Label("常规设置项2的布局"));
        return generalLayout;
    }

    /**
     * 创建外观设置项1的布局。
     *
     * @return 外观设置项1的布局节点
     */
    private Node createAppearanceSettingsLayout1() {
        VBox appearanceLayout = new VBox();
        appearanceLayout.getChildren().add(new Label("外观设置项1的布局"));
        return appearanceLayout;
    }

    /**
     * 创建外观设置项2的布局。
     *
     * @return 外观设置项2的布局节点
     */
    private Node createAppearanceSettingsLayout2() {
        VBox appearanceLayout = new VBox();
        appearanceLayout.getChildren().add(new Label("外观设置项2的布局"));
        return appearanceLayout;
    }

    /**
     * 创建安全设置项1的布局。
     *
     * @return 安全设置项1的布局节点
     */
    private Node createSecuritySettingsLayout1() {
        VBox securityLayout = new VBox();
        securityLayout.getChildren().add(new Label("安全设置项1的布局"));
        return securityLayout;
    }

    /**
     * 创建安全设置项2的布局。
     *
     * @return 安全设置项2的布局节点
     */
    private Node createSecuritySettingsLayout2() {
        VBox securityLayout = new VBox();
        securityLayout.getChildren().add(new Label("安全设置项2的布局"));
        return securityLayout;
    }
}
