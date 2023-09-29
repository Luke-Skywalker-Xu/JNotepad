package org.jcnc.jnotepad.views.manager;

import atlantafx.base.theme.Styles;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.event.handler.menubar.*;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.model.entity.ShortcutKey;
import org.jcnc.jnotepad.ui.pluginstage.PluginManagementPane;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.root.top.menu.TopMenuBar;
import org.slf4j.Logger;

import java.util.*;

import static org.jcnc.jnotepad.common.constants.AppConstants.*;
import static org.jcnc.jnotepad.common.constants.TextConstants.*;

/**
 * 顶部菜单栏管理类
 *
 * @author gewuyou
 */
public class TopMenuBarManager {
    private static final TopMenuBarManager INSTANCE = new TopMenuBarManager();
    private final TopMenuBar topMenuBar = TopMenuBar.getInstance();
    private final List<Menu> topMenu = new ArrayList<>();
    private final Map<String, MenuItem> fileMenuItems = new HashMap<>();

    private final Map<String, MenuItem> setMenuItems = new HashMap<>();

    private final Map<String, MenuItem> pluginMenuItems = new HashMap<>();

    private final Map<String, MenuItem> helpMenuItems = new HashMap<>();

    private final Map<String, RadioMenuItem> languageMenuItems = new HashMap<>();
    Logger logger = LogUtil.getLogger(this.getClass());
    AppConfigController appConfigController = AppConfigController.getInstance();

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
        toggleLanguageCheck(appConfigController.getLanguage());
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

        // 语言菜单
        registerLanguageMenuItem(topMenuBar.getChineseItem(), UPPER_CHINESE, Locale.CHINESE, this::toggleLanguage);
        registerLanguageMenuItem(topMenuBar.getEnglishItem(), UPPER_ENGLISH, Locale.ENGLISH, this::toggleLanguage);


        // 设置菜单
        registerSetMenuItem(topMenuBar.getLineFeedItem(), WORD_WRAP, "lineFeedItem", (observableValue, before, after) -> {
            // 1. 更新全局配置
            AppConfigController.getInstance().setAutoLineConfig(after);
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
        registerPluginMenuItem(topMenuBar.getPluginManagerItem(), MANAGER_PLUGIN, "pluginManagerItem", event -> {
            Stage newStage = new Stage();
            newStage.getIcons().add(UiUtil.getAppIcon());
            newStage.setTitle("插件管理");

            PluginManagementPane pluginManagementPane = new PluginManagementPane();

            Scene scene = new Scene(pluginManagementPane, 900, 600);
            newStage.setScene(scene);
            newStage.show();
        });
        registerPluginMenuItem(topMenuBar.getCountItem(), STATISTICS, "countItem", event -> {
        });

        //帮助菜单
        registerHelpMenuItem(topMenuBar.getAboutItem(), ABOUT, "aboutItem", event -> {
            Stage aboutStage = new Stage();
            String leftBtnText = " 确定 ";

            String rightBtnText = " 取消 ";
            Button leftBtn = new Button();
            leftBtn.getStyleClass().addAll(Styles.SMALL);
            leftBtn.setText(leftBtnText);

            Button rightBtn = new Button();
            rightBtn.getStyleClass().addAll(Styles.SMALL);
            rightBtn.setText(rightBtnText);
            aboutStage.getIcons().add(UiUtil.getAppIcon());
            aboutStage.setTitle("关于 " + APP_NAME);
            BorderPane root = new BorderPane();
            VBox textBox = new VBox();
            VBox iconBox = new VBox();
            ImageView iconImageView = new ImageView(new Image("icon.png"));
            iconImageView.setFitWidth(50);
            iconImageView.setFitHeight(50);
            iconBox.setPadding(new Insets(20));

            iconBox.getChildren().addAll(iconImageView);


            textBox.setPadding(new Insets(10));

            HBox titleBox = new HBox(5);
            titleBox.setPadding(new Insets(10, 0, 0, 0));

            Label titleLabel = new Label(APP_NAME);
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label versionLabel = new Label(VERSION);
            versionLabel.setPadding(new Insets(0.5, 0, 0, 0));
            versionLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

            titleBox.getChildren().addAll(titleLabel, versionLabel);

            Label descriptionLabel = new Label(APP_NAME + "是一款自由的集成开发环境。");
            descriptionLabel.setPadding(new Insets(8, 0, 8, 0));
            descriptionLabel.setStyle("-fx-font-size: 14px;");

            VBox linkBox = new VBox(7);
            Hyperlink repositoryLink = new Hyperlink();
            repositoryLink.setText("仓库地址");
            repositoryLink.setOnAction(event1 -> {
                openWebsite("https://gitee.com/jcnc-org/JNotepad");
            });
            repositoryLink.setVisited(true);
            repositoryLink.setMnemonicParsing(true);
            repositoryLink.setStyle("-color-link-fg-visited:-color-accent-fg;");

            Hyperlink feedBackLink = new Hyperlink();
            feedBackLink.setText("提交反馈");
            feedBackLink.setOnAction(event1 -> {
                openWebsite("https://gitee.com/jcnc-org/JNotepad/issues/new/choose");
            });
            feedBackLink.setVisited(true);
            feedBackLink.setMnemonicParsing(true);
            feedBackLink.setStyle("-color-link-fg-visited:-color-accent-fg;");

            Hyperlink qLink = new Hyperlink();
            qLink.setText("加入QQ群");
            qLink.setOnAction(event1 -> {
                openWebsite("https://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=x3QF-jrJAKTiwu8kV5-giBk2ow66Kzyr&authKey=qNqrQauD7Ra4fXH%2Ftu4ylHXCyrf2EOYj9oMYOmFjlzYmrgDL8Yd0m2qhrQQEBL25&noverify=0&group_code=386279455");
            });
            qLink.setVisited(true);
            qLink.setMnemonicParsing(true);
            qLink.setStyle("-color-link-fg-visited:-color-accent-fg;");

            linkBox.getChildren().addAll(repositoryLink, feedBackLink, qLink);

            Label authorLabel = new Label("Copyleft © 2023 " + AUTHOR + ".");
            authorLabel.setPadding(new Insets(8, 0, 8, 0));
            authorLabel.setStyle("-fx-font-size: 14px;");

            textBox.getChildren().addAll(titleBox, descriptionLabel, linkBox, authorLabel);

            HBox bottomBox = new HBox(10);
            bottomBox.setPadding(new Insets(7, 15, 7, 0));

            bottomBox.setAlignment(Pos.CENTER_RIGHT);

            bottomBox.getChildren().addAll(leftBtn, rightBtn);
            root.setBottom(bottomBox);

            root.setCenter(textBox);
            root.setLeft(iconBox);

            Scene scene = new Scene(root, 450, 240);
            aboutStage.setResizable (false);
            aboutStage.setScene(scene);
            aboutStage.show();

        });
    }

    /**
     * 打开网页方法
     */
    private void openWebsite(String url) {
        try {
            LogUtil.getLogger(this.getClass()).info("正在打开---" + url);
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            LogUtil.getLogger(this.getClass()).info("打开失败---" + url);
        }
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
        List<ShortcutKey> shortcutKeyConfigs = appConfigController.getShortcutKey();
        for (ShortcutKey shortcutKey : shortcutKeyConfigs) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = topMenuBar.getItemMap().get(shortcutKey.getButtonName());
            String shortKeyValue = shortcutKey.getShortcutKeyValue();
            if (Objects.isNull(menuItem)) {
                continue;
            }
            if ("".equals(shortKeyValue)) {
                itemsToUnbind.add(menuItem);
                continue;
            }
            logger.info("功能名称：{}->快捷键:{}", menuItem.getText(), shortKeyValue);
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
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
        menus.addAll(topMenu);
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
        topMenu.add(menu);
    }
}
