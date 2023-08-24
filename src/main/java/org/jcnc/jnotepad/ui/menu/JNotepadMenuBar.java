package org.jcnc.jnotepad.ui.menu;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.jcnc.jnotepad.app.config.GlobalConfig;
import org.jcnc.jnotepad.controller.event.handler.*;
import org.jcnc.jnotepad.init.Config;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 封装菜单栏组件。
 *
 * @author songdragon
 */
public class JNotepadMenuBar extends MenuBar {

    Config config = new Config();
    Properties properties = config.readPropertiesFromFile();
    String SAVA = properties.getProperty("SAVA");
    String FILE = properties.getProperty("FILE");
    String NEW = properties.getProperty("NEW");
    String OPEN = properties.getProperty("OPEN");
    String SAVA_AS = properties.getProperty("SAVA_AS");

    String SET = properties.getProperty("SET");

    String WORD_WRAP = properties.getProperty("WORD_WRAP");

    String PLUGIN = properties.getProperty("PLUGIN");

    String ADD_PLUGIN = properties.getProperty("ADD_PLUGIN");

    String STATISTICS = properties.getProperty("STATISTICS");

    private static final JNotepadMenuBar MENU_BAR = new JNotepadMenuBar();

    private JNotepadMenuBar() {
        init();
    }

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
     * 另存为
     */
    private MenuItem saveAsItem;
    /**
     * 保存
     */
    private MenuItem saveItem;
    /**
     * 增加
     */
    private MenuItem addItem;
    /**
     * 查看
     */
    private MenuItem countItem;


    /**
     * 自动换行点击菜单按钮
     */
    private CheckMenuItem lineFeedItem;

    private final Map<String, MenuItem> itemMap = new HashMap<>();

    /**
     * 初始化菜单栏
     */
    private void init() {
        initFileMenu();
        initSettingMenu();
        initPluginMenu();
        // 菜单栏
        this.getMenus().addAll(fileMenu, setMenu, pluginMenu);
        initEventHandlers();
    }

    /**
     * 初始化文件菜单
     */
    private void initFileMenu() {
        // 文件菜单
        fileMenu = new Menu(FILE);

        newItem = new MenuItem(NEW);
        itemMap.put("newItem", newItem);

        openItem = new MenuItem(OPEN);
        itemMap.put("openItem", openItem);

        saveItem = new MenuItem(SAVA);
        itemMap.put("saveItem", saveItem);

        saveAsItem = new MenuItem(SAVA_AS);
        itemMap.put("saveAsItem", saveAsItem);

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem);
    }

    /**
     * 初始化设置菜单
     */
    private void initSettingMenu() {
        // 设置菜单
        setMenu = new Menu(SET);

        lineFeedItem = new CheckMenuItem(WORD_WRAP);
        itemMap.put("lineFeedItem", lineFeedItem);
        lineFeedItem.selectedProperty().set(true);

        setMenu.getItems().addAll(lineFeedItem);
    }

    /**
     * 初始化插件菜单
     */
    private void initPluginMenu() {
        // 插件菜单
        pluginMenu = new Menu(PLUGIN);
        addItem = new MenuItem(ADD_PLUGIN);
        itemMap.put("addItem", addItem);

        countItem = new MenuItem(STATISTICS);
        itemMap.put("countItem", countItem);

        pluginMenu.getItems().addAll(addItem, countItem);
    }

    public static JNotepadMenuBar getMenuBar() {
        return MENU_BAR;
    }

    /**
     * 初始化事件处理
     */
    private void initEventHandlers() {
        newItem.setOnAction(new NewFile());
        openItem.setOnAction(new OpenFile());
        saveItem.setOnAction(new SaveFile());
        saveAsItem.setOnAction(new SaveAsFile());
        lineFeedItem.selectedProperty().addListener((observableValue, before, after) -> {
            // 1. 更新全局配置
            GlobalConfig.getConfig().setAutoLineConfig(after);
            // 2. 对当前tab生效配置
            JNotepadTabPane.getInstance().fireTabSelected();
        });
    }

    public Map<String, MenuItem> getItemMap() {
        return itemMap;
    }

    /**
     * 根据当前选中tab，更新菜单选项
     */
    public void updateMenuStatusBySelectedTab() {
        JNotepadTab selectedTab = JNotepadTabPane.getInstance().getSelected();
        lineFeedItem.selectedProperty().setValue(selectedTab.isAutoLine());
    }
}
