package org.jcnc.jnotepad.views.manager;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.common.util.LogUtil;
import org.jcnc.jnotepad.common.util.UiUtil;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.controller.event.handler.menubar.*;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.model.entity.ShortcutKey;
import org.jcnc.jnotepad.plugin.api.core.views.manager.AbstractManager;
import org.jcnc.jnotepad.ui.setstage.HelpPaneStage;
import org.jcnc.jnotepad.ui.setstage.pluginstage.PluginManagementPane;
import org.jcnc.jnotepad.views.root.top.menu.TopMenuBar;
import org.slf4j.Logger;

import java.util.*;

import static org.jcnc.jnotepad.common.constants.TextConstants.*;

/**
 * 顶部菜单栏管理类
 *
 * @author gewuyou
 */
public class TopMenuBarManager extends AbstractManager<Menu> {
    private static final TopMenuBarManager INSTANCE = new TopMenuBarManager();
    private final TopMenuBar topMenuBar = TopMenuBar.getInstance();
    private final List<Menu> nodeList = new ArrayList<>();
    private final Map<String, MenuItem> fileMenuItems = new HashMap<>();

    private final Map<String, MenuItem> setMenuItems = new HashMap<>();

    private final Map<String, MenuItem> pluginMenuItems = new HashMap<>();

    private final Map<String, MenuItem> helpMenuItems = new HashMap<>();

    private final Map<String, RadioMenuItem> languageMenuItems = new HashMap<>();
    Logger logger = LogUtil.getLogger(this.getClass());
    UserConfigController userConfigController = UserConfigController.getInstance();

    private TopMenuBarManager() {

    }

    public static TopMenuBarManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化菜单栏
     */

    public void initTopMenuBar() {
        topMenuBar.setPadding(new Insets(-3, 0, -3, 0));
        registerTopMenuBar();
        // 初始化文件菜单
        initFileMenu();
        // 初始化语言菜单
        initLanguageMenu();
        // 设置当前语言选中状态
        toggleLanguageCheck(userConfigController.getLanguage());
        // 初始化设置菜单
        initSettingMenu();
        // 初始化设置菜单
        initHelpMenu();
        // 初始化插件菜单
        initPluginMenu();

        // 刷新顶部菜单栏
        refreshTopMenuBar();
        // 初始化快捷键
        initShortcutKeys();
    }

    /**
     * 注册顶部菜单栏
     */
    public void registerTopMenuBar() {
        // 文件菜单
        registerFileMenuItem(topMenuBar.getNewItem(), NEW, "newItem", new NewFile());
        registerFileMenuItem(topMenuBar.getOpenItem(), OPEN, "openItem", new OpenFile());
        registerFileMenuItem(topMenuBar.getSaveItem(), SAVE, "saveItem", new SaveFile());
        registerFileMenuItem(topMenuBar.getSaveAsItem(), SAVE_AS, "saveAsItem", new SaveAsFile());
        registerFileMenuItem(topMenuBar.getRenameItem(), RENAME, "renameItem", new RenameFile());
        // 打开文件夹按钮
        registerFileMenuItem(topMenuBar.getOpenDirItem(), OPEN_DIRECTORY, "openDirItem", new OpenDirectory());

        // 语言菜单
        registerLanguageMenuItem(topMenuBar.getChineseItem(), UPPER_CHINESE, Locale.CHINESE, this::toggleLanguage);
        registerLanguageMenuItem(topMenuBar.getEnglishItem(), UPPER_ENGLISH, Locale.ENGLISH, this::toggleLanguage);


        // 设置菜单
        registerSetMenuItem(topMenuBar.getLineFeedItem(), WORD_WRAP, "lineFeedItem", (observableValue, before, after) -> {
            // 1. 更新全局配置
            UserConfigController.getInstance().setAutoLineConfig(after);
            // 2. 对当前tab生效配置
            CenterTabPaneManager.getInstance().fireTabSelected();
        });
        topMenuBar.getLineFeedItem().selectedProperty().set(true);

        registerSetMenuItem(topMenuBar.getTopItem(), TOP, "topItem", (observableValue, before, after) -> {
            // 获取窗口容器
            Stage primaryStage = (Stage) UiUtil.getAppWindow();
            // 设置窗口为置顶
            primaryStage.setAlwaysOnTop(after);
        });

        registerSetMenuItem(topMenuBar.getOpenConfigItem(), OPEN_CONFIGURATION_FILE, "openConfigItem", new OpenConfig());
        registerSetMenuItem(topMenuBar.getLanguageMenu(), LANGUAGE, "languageMenu", actionEvent -> {
        });

        //插件菜单
        registerPluginMenuItem(topMenuBar.getPluginManagerItem(), MANAGER_PLUGIN, "pluginManagerItem", event -> new PluginManagementPane().run());
        registerPluginMenuItem(topMenuBar.getCountItem(), STATISTICS, "countItem", event -> {
        });
        //帮助菜单
        registerHelpMenuItem(topMenuBar.getAboutItem(), ABOUT, "aboutItem", event -> new HelpPaneStage().run(new Stage()));
    }


    /**
     * 切换语言
     *
     * @param actionEvent 点击事件
     */
    private void toggleLanguage(ActionEvent actionEvent) {
        if (actionEvent == null) {
            return;
        }
        RadioMenuItem languageItem = (RadioMenuItem) actionEvent.getSource();
        if (languageItem == null) {
            return;
        }
        LocalizationController.setCurrentLocal((Locale) languageItem.getUserData());
    }

    /**
     * 设置当前语言选中状态
     *
     * @param language 语言
     */
    public void toggleLanguageCheck(String language) {
        languageMenuItems.forEach((k, v) -> v.setSelected(language.toUpperCase().equals(k)));
    }

    /**
     * 注册文件菜单项
     *
     * @param menuItem     菜单项
     * @param menuItemName 菜单项名称
     * @param buttonName   按钮名称
     * @param eventHandler 操作事件
     */
    public void registerFileMenuItem(MenuItem menuItem, String menuItemName, String buttonName, EventHandler<ActionEvent> eventHandler) {
        fileMenuItems.put(menuItemName, menuItem);
        setMenuItem(menuItem, buttonName, eventHandler);
    }

    /**
     * 注册帮助菜单项
     *
     * @param menuItem     菜单项
     * @param menuItemName 菜单项名称
     * @param buttonName   按钮名称
     * @param eventHandler 操作事件
     */
    public void registerHelpMenuItem(MenuItem menuItem, String menuItemName, String buttonName, EventHandler<ActionEvent> eventHandler) {
        helpMenuItems.put(menuItemName, menuItem);
        setMenuItem(menuItem, buttonName, eventHandler);
    }

    /**
     * 注册设置菜单项
     *
     * @param menuItem     菜单项
     * @param menuItemName 菜单项名称
     * @param buttonName   按钮名称
     * @param eventHandler 操作事件
     */
    public void registerSetMenuItem(MenuItem menuItem, String menuItemName, String buttonName, EventHandler<ActionEvent> eventHandler) {
        setMenuItems.put(menuItemName, menuItem);
        setMenuItem(menuItem, buttonName, eventHandler);
    }

    /**
     * 注册设置菜单项
     *
     * @param checkMenuItem 检查菜单项
     * @param menuItemName  菜单项名称
     * @param buttonName    按钮名称
     * @param listener      监听事件
     */

    public void registerSetMenuItem(CheckMenuItem checkMenuItem, String menuItemName, String buttonName, ChangeListener<Boolean> listener) {
        setMenuItems.put(menuItemName, checkMenuItem);
        setCheckMenuItem(checkMenuItem, buttonName, listener);
    }

    /**
     * 注册语言菜单
     *
     * @param radioMenuItem 单选菜单项
     * @param menuItemName  菜单项名称
     * @param locale        语言
     * @param eventHandler  操作事件
     */

    public void registerLanguageMenuItem(RadioMenuItem radioMenuItem, String menuItemName, Locale locale, EventHandler<ActionEvent> eventHandler) {
        languageMenuItems.put(menuItemName, radioMenuItem);
        setRadioMenuItem(radioMenuItem, locale, eventHandler);
    }

    /**
     * 注册插件菜单
     *
     * @param menuItem     菜单项
     * @param menuItemName 菜单项名称
     * @param buttonName   按钮名称
     * @param eventHandler 操作事件
     */
    public void registerPluginMenuItem(MenuItem menuItem, String menuItemName, String buttonName, EventHandler<ActionEvent> eventHandler) {
        pluginMenuItems.put(menuItemName, menuItem);
        setMenuItem(menuItem, buttonName, eventHandler);
    }

    /**
     * 注册菜单项
     *
     * @param menuItem     菜单项
     * @param buttonName   按钮名称
     * @param eventHandler 操作事件
     */
    private void setMenuItem(MenuItem menuItem, String buttonName, EventHandler<ActionEvent> eventHandler) {
        menuItem.setUserData(buttonName);
        menuItem.setOnAction(eventHandler);
    }

    /**
     * 注册单选菜单项
     *
     * @param radioMenuItem 单选菜单项
     * @param locale        语言
     * @param eventHandler  操作事件
     */
    private void setRadioMenuItem(RadioMenuItem radioMenuItem, Locale locale, EventHandler<ActionEvent> eventHandler) {
        radioMenuItem.setUserData(locale);
        radioMenuItem.setOnAction(eventHandler);
    }

    /**
     * 注册检查菜单项
     *
     * @param checkMenuItem 检查菜单项
     * @param buttonName    按钮名称
     * @param listener      监听事件
     */
    private void setCheckMenuItem(CheckMenuItem checkMenuItem, String buttonName, ChangeListener<Boolean> listener) {
        checkMenuItem.setUserData(buttonName);
        checkMenuItem.selectedProperty().addListener(listener);
    }

    /**
     * 初始化快捷键
     */
    public void initShortcutKeys() {
        List<MenuItem> itemsToUnbind = new ArrayList<>();
        List<ShortcutKey> shortcutKeyConfigs = userConfigController.getShortcutKey();
        for (ShortcutKey shortcutKey : shortcutKeyConfigs) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = topMenuBar.getItemMap().get(shortcutKey.getButtonName());
            String shortKeyValue = shortcutKey.getShortcutKeyValue();
            if ("".equals(shortKeyValue) && menuItem != null) {
                itemsToUnbind.add(menuItem);
                continue;
            }
            if (menuItem != null) {
                logger.info("功能名称：{}->快捷键:{}", menuItem.getText(), shortKeyValue);
                // 动态添加快捷键
                menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
            }
        }
        // 解绑需要解绑的快捷键
        itemsToUnbind.forEach(menuItem -> menuItem.setAccelerator(null));
    }

    /**
     * 初始化顶部菜单
     */
    private void refreshTopMenuBar() {
        ObservableList<Menu> menus = topMenuBar.getMenus();
        menus.clear();
        menus.addAll(nodeList);
    }

    /**
     * 初始化插件菜单
     */
    private void initPluginMenu() {
        logger.info("初始化插件菜单!");
        var pluginMenu = topMenuBar.getPluginMenu();
        // 插件菜单
        UiResourceBundle.bindStringProperty(pluginMenu.textProperty(), PLUGIN);
        initMenuItems(pluginMenuItems, pluginMenu);
    }

    /**
     * 初始化插件菜单
     */
    private void initHelpMenu() {
        logger.info("初始化帮助菜单!");
        var helpMenu = topMenuBar.getHelpMenu();
        // 插件菜单
        UiResourceBundle.bindStringProperty(helpMenu.textProperty(), HELP);

        initMenuItems(helpMenuItems, helpMenu);
    }

    /**
     * 初始化设置菜单
     */
    private void initSettingMenu() {
        logger.info("初始化设置菜单!");
        var setMenu = topMenuBar.getSetMenu();
        // 设置菜单
        UiResourceBundle.bindStringProperty(setMenu.textProperty(), SET);
        // 初始化菜单项
        initMenuItems(setMenuItems, setMenu);
    }

    /**
     * 初始化语言菜单
     */
    private void initLanguageMenu() {
        logger.info("初始化语言菜单!");
        // 语言菜单
        ToggleGroup languageToggleGroup = new ToggleGroup();
        var itemMap = topMenuBar.getItemMap();
        languageMenuItems.forEach((key, value) -> {
            UiResourceBundle.bindStringProperty(value.textProperty(), key);
            itemMap.put(key, value);
            languageToggleGroup.getToggles().add(value);
            topMenuBar.getLanguageMenu().getItems().add(value);
        });
    }

    /**
     * 初始化文件菜单
     */
    private void initFileMenu() {
        logger.info("初始化文件菜单!");
        Menu fileMenu = topMenuBar.getFileMenu();
        // 文件菜单
        UiResourceBundle.bindStringProperty(fileMenu.textProperty(), FILE);
        // 初始化菜单项
        initMenuItems(fileMenuItems, fileMenu);
    }

    /**
     * 初始化菜单项
     *
     * @param menuItems 菜单项集合
     * @param menu      菜单
     */

    private void initMenuItems(Map<String, MenuItem> menuItems, Menu menu) {
        var itemMap = topMenuBar.getItemMap();
        menuItems.forEach((key, value) -> {
            UiResourceBundle.bindStringProperty(value.textProperty(), key);
            itemMap.put((String) value.getUserData(), value);
            menu.getItems().add(value);
        });
        registerNode(menu);
    }

    /**
     * 获取节点列表
     *
     * @return 节点列表
     */
    @Override
    public List<Menu> getNodeList() {
        return nodeList;
    }
}
