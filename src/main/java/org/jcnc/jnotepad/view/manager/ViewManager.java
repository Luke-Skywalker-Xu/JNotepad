package org.jcnc.jnotepad.view.manager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 该类管理记事本应用程序的视图组件。
 */
public class ViewManager {

    public static Label enCodingLabel; // 显示文本编码

    public static int tabIndex = 0;

    public static Boolean Line = true;

    // 菜单栏组件
    public static MenuBar menuBar; //菜单栏
    public static Menu fileMenu, setMenu; //文件菜单
    public static MenuItem newItem, openItem, saveAsItem; //新建/打开/保存/保存至 菜单

    public static CheckMenuItem lineFeedItem;
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
        setMenu = new Menu("设置");
        newItem = new MenuItem("新建");
        openItem = new MenuItem("打开");
        saveAsItem = new MenuItem("另存为");
        lineFeedItem = new CheckMenuItem("自动换行");
        lineFeedItem.selectedProperty().set(true);
        fileMenu.getItems().addAll(newItem, openItem, saveAsItem);
        setMenu.getItems().add(lineFeedItem);

        menuBar.getMenus().addAll(fileMenu, setMenu);

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

        scene.setRoot(root);
    }
}
