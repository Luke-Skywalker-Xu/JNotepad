package org.jcnc.jnotepad.view.manager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;


/**
 * 该类管理记事本应用程序的视图组件。
 *
 * @author 许轲
 */
public class ViewManager {

    /**
     * 显示文本编码
     */
    private Label enCodingLabel; // 显示文本编码

    private int tabIndex = 0;

    private Boolean line = true;

    /// 菜单栏组件

    /**
     * 菜单栏
     */
    private MenuBar menuBar;
    /**
     * 文件菜单
     */
    private Menu fileMenu;
    /**
     * 插件菜单
     */
    private Menu setMenu;
    /**
     * 插件菜单
     */
    private Menu pluginMenu;

    ///  菜单按钮

    /**
     * 新建
     */
    private MenuItem newItem;
    /**
     * 打开
     */
    private MenuItem openItem;
    /**
     * 保存
     */
    private MenuItem saveAsItem;
    /**
     * 增加
     */
    private MenuItem addItem;
    /**
     * 查看
     */
    private MenuItem countItem;


    private CheckMenuItem lineFeedItem; // 自动换行点击菜单按钮
    // 主界面布局
    private BorderPane root; // 主布局

    // 多个标签页
    private TabPane tabPane; // 标签页栏

    // 状态栏
    private Label statusLabel;

    private static ViewManager instance = null;

    private Map<String, MenuItem> itemMap = new HashMap<>();


    public Label getEnCodingLabel() {
        return enCodingLabel;
    }
    /**
     * 自增并获取标签页索引
     *
     *
     * @return int 标签页索引
     * @apiNote ++tabIndex
     */

    public int selfIncreaseAndGetTabIndex() {
        return ++tabIndex;
    }

    public Boolean getLine() {
        return line;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getFileMenu() {
        return fileMenu;
    }

    public Menu getSetMenu() {
        return setMenu;
    }

    public Menu getPluginMenu() {
        return pluginMenu;
    }

    public MenuItem getNewItem() {
        return newItem;
    }

    public MenuItem getOpenItem() {
        return openItem;
    }

    public MenuItem getSaveAsItem() {
        return saveAsItem;
    }

    public MenuItem getAddItem() {
        return addItem;
    }

    public MenuItem getCountItem() {
        return countItem;
    }

    public CheckMenuItem getLineFeedItem() {
        return lineFeedItem;
    }

    public BorderPane getRoot() {
        return root;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Map<String, MenuItem> getItemMap() {
        return itemMap;
    }


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

    public static ViewManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new RuntimeException("ViewManager的实例未初始化!");
        }
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

        // 文件菜单
        fileMenu = new Menu("文件");

        newItem = new MenuItem("新建");
        itemMap.put("newItem", newItem);

        openItem = new MenuItem("打开");
        itemMap.put("openItem", openItem);

        saveAsItem = new MenuItem("另存为");
        itemMap.put("saveAsItem", saveAsItem);

        fileMenu.getItems().addAll(newItem, openItem, saveAsItem);

        // 设置菜单
        setMenu = new Menu("设置");

        lineFeedItem = new CheckMenuItem("自动换行");
        itemMap.put("lineFeedItem", lineFeedItem);
        lineFeedItem.selectedProperty().set(true);

        setMenu.getItems().addAll(lineFeedItem);

        // 插件菜单
        pluginMenu = new Menu("插件");
        addItem = new MenuItem("增加插件");
        itemMap.put("addItem", addItem);

        countItem = new MenuItem("统计字数");
        itemMap.put("countItem", countItem);

        pluginMenu.getItems().addAll(addItem, countItem);

        // 菜单栏
        menuBar.getMenus().addAll(fileMenu, setMenu, pluginMenu);

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
