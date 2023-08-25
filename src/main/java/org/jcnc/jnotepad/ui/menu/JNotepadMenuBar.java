package org.jcnc.jnotepad.ui.menu;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.config.GlobalConfig;
import org.jcnc.jnotepad.controller.event.handler.*;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.constants.TextConstants.*;

/**
 * 封装菜单栏组件。
 *
 * @author songdragon
 */
public class JNotepadMenuBar extends MenuBar {


    /**
     * 标签页布局组件封装。
     */
    JNotepadTabPane jNotepadTabPane = JNotepadTabPane.getInstance();

    private static final JNotepadMenuBar MENU_BAR = new JNotepadMenuBar();

    private JNotepadMenuBar() {
        init();
    }

    /**
     * 文件菜单
     */
    private Menu fileMenu;
    /**
     * 设置菜单
     */
    private Menu setMenu;
    /**
     * 插件菜单
     */
    private Menu pluginMenu;

    /**
     * 语言菜单
     */
    private Menu languageMenu;

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
     * 打开配置文件
     */
    private MenuItem openConfigItem;
    /**
     * 自动换行点击菜单按钮
     */
    private CheckMenuItem lineFeedItem;
    /**
     * 置顶按钮
     */
    private CheckMenuItem topItem;

    /**
     * 中文选项
     */
    private MenuItem chineseItem;
    /**
     * 英文选项
     */
    private MenuItem englishItem;
    private final Map<String, MenuItem> itemMap = new HashMap<>();

    /**
     * 初始化菜单栏
     */
    private void init() {
        initFileMenu();
        initLanguageMenu();
        initSettingMenu();
        initPluginMenu();

        // 菜单栏
        this.getMenus().addAll(fileMenu, setMenu, pluginMenu);
        initEventHandlers();
    }

    /**
     * 初始化语言菜单
     */
    private void initLanguageMenu() {
        // 语言菜单
        languageMenu = new Menu(LANGUAGE);

        chineseItem = new MenuItem(CHINESE);
        itemMap.put("chineseItem", chineseItem);

        englishItem = new MenuItem(ENGLISH);
        itemMap.put("englishItem", englishItem);

        languageMenu.getItems().addAll(chineseItem, englishItem);
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

        topItem = new CheckMenuItem(TOP);
        itemMap.put("topItem", topItem);

        openConfigItem = new MenuItem(OPEN_CONFIGURATION_FILE);
        itemMap.put("openConfigItem", openConfigItem);

        itemMap.put("languageMenu", languageMenu);
        setMenu.getItems().addAll(lineFeedItem, openConfigItem, topItem, languageMenu);
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
        openConfigItem.setOnAction(new OpenConfig());
        lineFeedItem.selectedProperty().addListener((observableValue, before, after) -> {
            // 1. 更新全局配置
            GlobalConfig.getConfig().setAutoLineConfig(after);
            // 2. 对当前tab生效配置
            jNotepadTabPane.fireTabSelected();
        });
        topItem.selectedProperty().addListener((observableValue, before, after) -> {
            // 获取窗口容器
            Stage primaryStage = (Stage) this.getScene().getWindow();
            // 设置窗口为置顶
            primaryStage.setAlwaysOnTop(after);
        });

    }

    public Map<String, MenuItem> getItemMap() {
        return itemMap;
    }

    /**
     * 根据当前选中tab，更新菜单选项
     */
    public void updateMenuStatusBySelectedTab() {
        JNotepadTab selectedTab = jNotepadTabPane.getSelected();
        lineFeedItem.selectedProperty().setValue(selectedTab.isAutoLine());
    }
}
