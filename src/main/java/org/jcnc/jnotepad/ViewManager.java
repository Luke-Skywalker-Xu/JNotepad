package org.jcnc.jnotepad;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 该类管理记事本应用程序的视图组件。
 */
public class ViewManager {
    public static final DataFormat TAB_DATA_FORMAT = new DataFormat("draggable-tab");

    public static Label enCodingLabel; // 显示文本编码

    public static int tabIndex = 0;

    // 菜单栏组件
    public static MenuBar menuBar; //菜单栏
    public static Menu fileMenu; //文件菜单
    public static MenuItem newItem, openItem, saveItem, saveAsItem; //新建/打开/保存/保存至 菜单

    // 主界面布局
    public static BorderPane root; //主布局

    // 多个标签页
    public static TabPane tabPane; //标签页栏

    // 状态栏
    public static Label statusLabel;

    private static ViewManager instance = null;

    /**
     * 获取ViewManager的实例。如果实例不存在，则创建一个新实例。
     *
     * @param scene 与视图相关联的JavaFX场景。
     * @return ViewManager的实例。
     */
    public static ViewManager getInstance(Scene scene) {
        if (instance == null) {
            instance = new ViewManager(scene);
        }
        return instance;
    }

    /**
     * 构造函数。设置场景和根布局。
     *
     * @param scene 与视图相关联的JavaFX场景。
     */
    private ViewManager(Scene scene) {
        root = new BorderPane();
        scene.setRoot(root);
    }

    /**
     * 初始化屏幕组件。
     *
     * @param scene 与视图相关联的JavaFX场景。
     */
    public void initScreen(Scene scene) {
        // 创建菜单栏并添加菜单项
        menuBar = new MenuBar();
        fileMenu = new Menu("文件");
        newItem = new MenuItem("新建");
        openItem = new MenuItem("打开");
        saveItem = new MenuItem("保存");
        saveAsItem = new MenuItem("另存为");
        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem);
        menuBar.getMenus().add(fileMenu);


        // 创建主界面布局
        root = new BorderPane();
        root.setTop(menuBar);

        // 创建标签页和文本编辑区域
        tabPane = new TabPane();
        root.setCenter(tabPane);

        // 创建状态栏
        statusLabel = new Label("行数：1 \t列数：1 \t字数：0 ");

        enCodingLabel = new Label(); // 创建新的标签以显示编码信息
        HBox statusBox = new HBox(statusLabel, enCodingLabel); // 使用HBox放置状态标签和编码标签
        root.setBottom(statusBox);
        BorderPane.setMargin(statusBox, new Insets(5, 10, 5, 10));

        // 给每个标签添加拖放功能
        for (Tab tab : tabPane.getTabs()) {
            addDragAndDropFunctionalityToTab(tab);
        }
        scene.setRoot(root);
    }

    /**
     * 为标签添加拖放功能。
     *
     * @param tab 要添加拖放功能的标签。
     */
    private void addDragAndDropFunctionalityToTab(Tab tab) {
        tab.getTabPane().setOnDragDetected(event -> {
            Dragboard dragboard = tab.getTabPane().startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(TAB_DATA_FORMAT, tab);
            dragboard.setContent(content);
        });

        // 可选：为放置目标添加放置处理器（根据你的需求）
        tab.getTabPane().setOnDragOver(event -> {
            if (event.getDragboard().hasContent(TAB_DATA_FORMAT)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        tab.getTabPane().setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasContent(TAB_DATA_FORMAT)) {
                Tab draggedTab = (Tab) dragboard.getContent(TAB_DATA_FORMAT);
                // 在这里处理放置操作，例如重新排列标签
                // 这可能需要修改你的标签的位置或交换标签的位置
                event.setDropCompleted(true);
            }
            event.consume();
        });
    }
}
