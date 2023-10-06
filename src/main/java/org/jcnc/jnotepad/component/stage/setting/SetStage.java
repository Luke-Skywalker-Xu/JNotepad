package org.jcnc.jnotepad.component.stage.setting;

import atlantafx.base.controls.CustomTextField;
import atlantafx.base.theme.Styles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.config.AppConfig;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.app.manager.ApplicationManager;
import org.jcnc.jnotepad.common.constants.TextConstants;
import org.jcnc.jnotepad.common.manager.ApplicationCacheManager;
import org.jcnc.jnotepad.component.stage.dialog.factory.impl.BasicDirectoryChooserFactory;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.event.handler.toolbar.OpenDirectory;
import org.jcnc.jnotepad.model.entity.Cache;
import org.jcnc.jnotepad.model.enums.CacheExpirationTime;
import org.jcnc.jnotepad.plugin.PluginManagerInterface;
import org.jcnc.jnotepad.util.PopUpUtil;
import org.jcnc.jnotepad.util.UiUtil;

import java.io.File;

import static org.jcnc.jnotepad.common.constants.AppConstants.SCREEN_LENGTH;
import static org.jcnc.jnotepad.common.constants.AppConstants.SCREEN_WIDTH;

/**
 * SetStage类表示设置窗口的单例对象。此窗口用于显示不同的设置选项和其对应的布局。
 * 通过调用getInstance方法获取SetStage的实例，并使用openSetStage方法打开设置窗口。
 *
 * @author luke
 */
public class SetStage {

    public static final String GENERAL_SETTING_1 = "常规设置项1";
    public static final String GENERAL_SETTING_2 = "常规设置项2";
    public static final String APPEARANCE_SETTING_1 = "外观设置项1";
    public static final String APPEARANCE_SETTING_2 = "外观设置项2";
    public static final String PLUGINS = "插件";
    public static final String SECURITY_SETTING_1 = "安全设置项1";
    public static final String SECURITY_SETTING_2 = "安全设置项2";

    private static SetStage instance;
    private StackPane contentDisplay;

    private final ApplicationCacheManager cacheManager = ApplicationCacheManager.getInstance();

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
        primaryStage.getIcons().add(UiUtil.getAppIcon());
        primaryStage.setTitle("设置窗口");
        // 将窗口设置为模态
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        contentDisplay = new StackPane();

        TreeView<String> treeView = createTreeView();

        SplitPane splitPane = new SplitPane(treeView, contentDisplay);
        splitPane.setDividerPositions(0.3);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setStyle("-fx-background-color: rgba(43,43,43,0.12);");
        bottomBox.setPadding(new Insets(7, 15, 7, 0));
        Button confirmButton = new Button(" 确认 ");
        confirmButton.setTextFill(Color.WHITE);

        confirmButton.getStyleClass().addAll(Styles.SMALL);
        confirmButton.setStyle("-fx-background-color: rgb(54,88,128);");
        CustomSetButton cancelButton = new CustomSetButton(" 取消 ");
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
        cancelButton.getStyleClass().addAll(Styles.SMALL);
        Button applicationButton = new Button(" 应用 ");
        applicationButton.getStyleClass().addAll(Styles.SMALL);
        bottomBox.getChildren().addAll(confirmButton, cancelButton, applicationButton);

        BorderPane root = new BorderPane();
        root.setCenter(splitPane);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, SCREEN_WIDTH - 100, SCREEN_LENGTH - 80);

        primaryStage.getIcons().add(UiUtil.getAppIcon());

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
        TreeItem<String> generalItem = new TreeItem<>("常规");

        TreeItem<String> generalItem1 = new TreeItem<>("常规设置项1");
        TreeItem<String> generalItem2 = new TreeItem<>("常规设置项2");
        generalItem.getChildren().add(generalItem1);
        generalItem.getChildren().add(generalItem2);


        //外观设置树
        TreeItem<String> appearanceItem = new TreeItem<>("外观");

        TreeItem<String> appearanceItem1 = new TreeItem<>("外观设置项1");
        TreeItem<String> appearanceItem2 = new TreeItem<>("外观设置项2");
        appearanceItem.getChildren().add(appearanceItem1);
        appearanceItem.getChildren().add(appearanceItem2);

        //安全设置树
        TreeItem<String> securityItem = new TreeItem<>("安全");

        TreeItem<String> securityItem1 = new TreeItem<>("安全设置项1");
        TreeItem<String> securityItem2 = new TreeItem<>("安全设置项2");

        TreeItem<String> pluginsItem = new TreeItem<>(PLUGINS);

        securityItem.getChildren().add(securityItem1);
        securityItem.getChildren().add(securityItem2);



        root.getChildren().add(generalItem);
        root.getChildren().add(appearanceItem);
        root.getChildren().add(securityItem);
        root.getChildren().add(pluginsItem);
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
            case GENERAL_SETTING_1 -> createGeneralSettingsLayout1();
            case GENERAL_SETTING_2 -> createGeneralSettingsLayout2();
            case APPEARANCE_SETTING_1 -> createAppearanceSettingsLayout1();
            case APPEARANCE_SETTING_2 -> createAppearanceSettingsLayout2();
            case SECURITY_SETTING_1 -> createSecuritySettingsLayout1();
            case SECURITY_SETTING_2 -> createSecuritySettingsLayout2();
//            case PLUGINS -> createPluginsLayout();
            default -> null;
        };
    }

    private Node createPluginsLayout() {
        VBox generalLayout = new VBox(10);
        generalLayout.setPadding(new Insets(25));
        PluginManagerInterface pluginManagerInterface = new PluginManagerInterface();
        Stage stage = new Stage();
        stage.getIcons().add(UiUtil.getAppIcon());
        pluginManagerInterface.start(stage);
        return generalLayout;
    }



    /**
     * 创建常规设置项1的布局。
     *
     * @return 常规设置项1的布局节点
     */
    private Node createGeneralSettingsLayout1() {
        VBox generalLayout = new VBox(10);
        generalLayout.setPadding(new Insets(25));

        var hBox = new HBox(5);

        var fileChooseText = new Text("文件根路径: ");
        fileChooseText.setFont(new Font(18));
        AppConfig config = AppConfigController.getInstance().getConfig();
        var fileChoose = new CustomTextField(config.getRootPath());
        fileChoose.getStyleClass().add(Styles.SMALL);
        fileChoose.setPrefWidth(420);

        var fileChooseBtn = new Button();
        fileChooseBtn.setText("选择文件夹");
        fileChooseBtn.getStyleClass().addAll(Styles.SMALL);
        BasicDirectoryChooserFactory directoryChooserFactory = BasicDirectoryChooserFactory.getInstance();
        fileChooseBtn.setOnAction(event -> {
            // 获取打开目录缓存
            Cache cache = cacheManager.getCache(OpenDirectory.GROUP, "openDirectory");

            File file = directoryChooserFactory.createDirectoryChooser(
                            UiResourceBundle.getContent(TextConstants.OPEN),
                            cache == null ? null : new File((String) cache.getCacheData()))
                    .showDialog(UiUtil.getAppWindow());
            if (file == null) {
                return;
            }
            if (file.equals(new File(config.getRootPath()))) {
                PopUpUtil.errorAlert("错误", "路径不能和默认路径相同", "请重新选择路径", null, null);
                return;
            }
            // 设置缓存
            if (cache == null) {
                cacheManager.addCache(cacheManager.createCache(OpenDirectory.GROUP, "openDirectory", file.getAbsolutePath(), CacheExpirationTime.NEVER_EXPIRES.getValue()));
            } else {
                cache.setCacheData(file.getParent());
                cacheManager.addCache(cache);
            }
            // 设置上次的根路径
            config.setLastRootPath(config.getRootPath());
            // 设置当前根路径
            config.setRootPath(file.getAbsolutePath());
            PopUpUtil.questionAlert("更改", "设置程序文件根路径", "设置成功，请重启程序以应用路径更改!", appDialog -> {
                appDialog.close();
                ApplicationManager.getInstance().restart();
            }, null, "重启", "以后再说");
            Stage stage = (Stage) fileChooseBtn.getScene().getWindow();
            stage.close();
        });
        hBox.getChildren().addAll(fileChooseText, fileChoose, fileChooseBtn);

        generalLayout.getChildren().addAll(hBox);

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
