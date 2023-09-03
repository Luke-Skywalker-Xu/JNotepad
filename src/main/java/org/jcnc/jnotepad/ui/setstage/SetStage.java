package org.jcnc.jnotepad.ui.setstage;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jcnc.jnotepad.tool.UiUtil;

/**
 * 设置窗口管理类，实现了单例模式。
 *
 * <p>该类用于管理设置窗口的显示和内容。</p>
 *
 * @author 许轲
 */
public class SetStage {

    /**
     * 唯一的 SetStage 实例，使用单例模式
     */
    private static final SetStage SET_STAGE = new SetStage();
    private StackPane contentDisplay;

    private SetStage() {
        // 私有构造方法以实现单例模式
    }

    /**
     * 获取 SetStage 的唯一实例。
     *
     * @return SetStage 的实例
     */
    public static SetStage getInstance() {
        return SET_STAGE;
    }

    /**
     * 打开设置窗口。
     */
    public void openSetStage() {
        // 创建一个用于显示内容的 StackPane，并初始化为默认显示的标签
        contentDisplay = new StackPane(new Label("从左侧选择设置以显示内容"));

        // 创建 TreeView，并初始化设置项
        TreeView<String> settingsTreeView = createTreeView();

        // 创建一个分割面板，左侧是设置树，右侧是内容显示区域
        SplitPane splitPane = new SplitPane(settingsTreeView, contentDisplay);
        // 设置分割位置
        splitPane.setDividerPositions(0.3);

        // 创建设置窗口的场景
        Scene scene = new Scene(splitPane, 800, 600);

        // 创建设置窗口的 Stage，并设置标题和场景
        Stage setStage = new Stage();
        setStage.setScene(scene);
        setStage.setTitle("设置窗口");
        setStage.getIcons().add(UiUtil.getAppIcon());
        setStage.show();
    }

    /**
     * 创建一个 TreeView 并初始化项。
     *
     * @return 初始化后的 TreeView
     */
    private TreeView<String> createTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("设置");
        rootItem.setExpanded(true);

        TreeItem<String> generalItem = new TreeItem<>("常规设置");
        TreeItem<String> appearanceItem = new TreeItem<>("外观设置");
        TreeItem<String> securityItem = new TreeItem<>("安全设置");

        generalItem.getChildren().add(new TreeItem<>("常规设置项1"));
        generalItem.getChildren().add(new TreeItem<>("常规设置项2"));

        appearanceItem.getChildren().add(new TreeItem<>("外观设置项1"));
        appearanceItem.getChildren().add(new TreeItem<>("外观设置项2"));

        securityItem.getChildren().add(new TreeItem<>("安全设置项1"));
        securityItem.getChildren().add(new TreeItem<>("安全设置项2"));

        rootItem.getChildren().add(generalItem);
        rootItem.getChildren().add(appearanceItem);
        rootItem.getChildren().add(securityItem);

        TreeView<String> treeView = new TreeView<>(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // 更新内容显示区域为选定项的设置内容标签
                contentDisplay.getChildren().setAll(new Label(newValue.getValue() + " 的设置内容"));
            }
        });

        return treeView;
    }
}
