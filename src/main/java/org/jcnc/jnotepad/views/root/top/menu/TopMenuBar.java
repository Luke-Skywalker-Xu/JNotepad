package org.jcnc.jnotepad.views.root.top.menu;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.event.handler.menubar.*;
import org.jcnc.jnotepad.controller.event.handler.util.SetBtn;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.model.entity.ShortcutKey;
import org.jcnc.jnotepad.plugin.PluginManagerInterface;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.util.UiUtil;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTabPane;
import org.jcnc.jnotepad.views.root.left.sidebar.tools.SidebarToolBar;
import org.slf4j.Logger;

import java.util.*;

import static org.jcnc.jnotepad.common.constants.TextConstants.*;

/**
 * 封装菜单栏组件。
 *
 * @author songdragon
 */
public class TopMenuBar extends MenuBar {

    private static final TopMenuBar MENU_BAR = new TopMenuBar();
    /**
     * 按钮集合
     */
    private final Map<String, MenuItem> itemMap = new HashMap<>();
    /**
     * 标签页布局组件封装。
     */
    CenterTabPane centerTabPane = CenterTabPane.getInstance();
    AppConfigController appConfigController = AppConfigController.getInstance();
    Logger logger = LogUtil.getLogger(this.getClass());
    /**
     * 工具栏
     */
    SidebarToolBar sidebarToolBar = SidebarToolBar.getInstance();
    /**
     * 获取工具栏中的setButton
     */
    Button setButton = sidebarToolBar.getSetButton();
    /**
     * 文件菜单
     */
    private Menu fileMenu;
    /**
     * 设置菜单
     */
    private Menu setMenu;

    ///  菜单按钮
    /**
     * 插件菜单
     */
    private Menu pluginMenu;
    /**
     * 语言菜单
     */
    private Menu languageMenu;
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
     * 重命名
     */
    private MenuItem renameItem;
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
    private RadioMenuItem chineseItem;
    /**
     * 英文选项
     */
    private RadioMenuItem englishItem;

    private TopMenuBar() {
        initMenuBar();
    }

    public static TopMenuBar getInstance() {
        return MENU_BAR;
    }

    /**
     * 设置当前语言选中状态
     *
     * @param language 语言
     */
    public void toggleLanguageCheck(String language) {
        switch (language) {
            case CHINESE -> chineseItem.setSelected(true);
            case ENGLISH -> englishItem.setSelected(true);
            default -> logger.error("未知语言:{}", language);
        }
    }


    /**
     * 初始化菜单栏
     */
    public void initMenuBar() {
        setPadding(new Insets(-3,0,-3,0));
        initFileMenu();
        initLanguageMenu();
        initSettingMenu();
        initPluginMenu();
        this.getMenus().clear();
        // 菜单栏
        this.getMenus().addAll(fileMenu, setMenu, pluginMenu);
        initEventHandlers();
        initShortcutKeys();
        toggleLanguageCheck(appConfigController.getLanguage());
    }

    /**
     * 初始化快捷键。
     */
    public void initShortcutKeys() {
        List<ShortcutKey> shortcutKeyConfigs = appConfigController.getShortcutKey();
        //FIXME: 如果shortcutKey不存在，需要解绑已绑定的快捷键
        for (ShortcutKey shortcutKey : shortcutKeyConfigs) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = this.itemMap.get(shortcutKey.getButtonName());
            String shortKeyValue = shortcutKey.getShortcutKeyValue();
            if (Objects.isNull(menuItem) || "".equals(shortKeyValue)) {
                continue;
            }
            logger.info("功能名称：{}->快捷键:{}", menuItem.getText(), shortKeyValue);
            // 动态添加快捷键
            menuItem.setAccelerator(KeyCombination.keyCombination(shortKeyValue));
        }
    }

    /**
     * 初始化语言菜单
     */
    private void initLanguageMenu() {
        logger.info("初始化语言菜单!");
        // 语言菜单
        languageMenu = new Menu();
        UiResourceBundle.bindStringProperty(languageMenu.textProperty(), LANGUAGE);
        ToggleGroup languageToggleGroup = new ToggleGroup();

        chineseItem = new RadioMenuItem();
        UiResourceBundle.bindStringProperty(chineseItem.textProperty(), UPPER_CHINESE);
        chineseItem.setUserData(Locale.CHINESE);
        itemMap.put("chineseItem", chineseItem);
        languageToggleGroup.getToggles().add(chineseItem);

        englishItem = new RadioMenuItem();
        UiResourceBundle.bindStringProperty(englishItem.textProperty(), UPPER_ENGLISH);
        englishItem.setUserData(Locale.ENGLISH);
        itemMap.put("englishItem", englishItem);
        languageToggleGroup.getToggles().add(englishItem);

        languageMenu.getItems().addAll(chineseItem, englishItem);

    }

    /**
     * 初始化文件菜单
     */
    private void initFileMenu() {
        logger.info("初始化文件菜单!");
        // 文件菜单
        fileMenu = new Menu();
        UiResourceBundle.bindStringProperty(fileMenu.textProperty(), FILE);

        newItem = new MenuItem();
        UiResourceBundle.bindStringProperty(newItem.textProperty(), NEW);

        itemMap.put("newItem", newItem);

        openItem = new MenuItem();
        UiResourceBundle.bindStringProperty(openItem.textProperty(), OPEN);
        itemMap.put("openItem", openItem);

        saveItem = new MenuItem();
        UiResourceBundle.bindStringProperty(saveItem.textProperty(), SAVE);
        itemMap.put("saveItem", saveItem);

        saveAsItem = new MenuItem();
        UiResourceBundle.bindStringProperty(saveAsItem.textProperty(), SAVE_AS);
        itemMap.put("saveAsItem", saveAsItem);

        renameItem = new MenuItem();
        UiResourceBundle.bindStringProperty(renameItem.textProperty(), RENAME);
        itemMap.put("renameItem", renameItem);

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem, renameItem);
    }

    /**
     * 初始化设置菜单
     */
    private void initSettingMenu() {
        logger.info("初始化设置菜单");
        // 设置菜单
        setMenu = new Menu();
        UiResourceBundle.bindStringProperty(setMenu.textProperty(), SET);

        lineFeedItem = new CheckMenuItem();
        UiResourceBundle.bindStringProperty(lineFeedItem.textProperty(), WORD_WRAP);
        itemMap.put("lineFeedItem", lineFeedItem);
        lineFeedItem.selectedProperty().set(true);

        topItem = new CheckMenuItem();
        UiResourceBundle.bindStringProperty(topItem.textProperty(), TOP);
        itemMap.put("topItem", topItem);

        openConfigItem = new MenuItem();
        UiResourceBundle.bindStringProperty(openConfigItem.textProperty(), OPEN_CONFIGURATION_FILE);
        itemMap.put("openConfigItem", openConfigItem);

        itemMap.put("languageMenu", languageMenu);
        setMenu.getItems().addAll(lineFeedItem, openConfigItem, topItem, languageMenu);
    }

    /**
     * 初始化插件菜单
     */
    private void initPluginMenu() {
        logger.info("初始化插件菜单!");
        // 插件菜单
        pluginMenu = new Menu();
        UiResourceBundle.bindStringProperty(pluginMenu.textProperty(), PLUGIN);

        addItem = new MenuItem();
        addItem.setOnAction(event -> {
            PluginManagerInterface pluginManagerInterface = PluginManagerInterface.getInstance();
            Stage stage = new Stage();
            stage.getIcons().add(UiUtil.getAppIcon());
            pluginManagerInterface.start(stage);
        });
        UiResourceBundle.bindStringProperty(addItem.textProperty(), ADD_PLUGIN);
        itemMap.put("addItem", addItem);

        countItem = new MenuItem();
        UiResourceBundle.bindStringProperty(countItem.textProperty(), STATISTICS);
        itemMap.put("countItem", countItem);

        pluginMenu.getItems().addAll(addItem, countItem);
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
        renameItem.setOnAction(new RenameFile());
        setButton.setOnAction(new SetBtn());
        lineFeedItem.selectedProperty().addListener((observableValue, before, after) -> {
            // 1. 更新全局配置
            AppConfigController.getInstance().setAutoLineConfig(after);
            // 2. 对当前tab生效配置
            centerTabPane.fireTabSelected();
        });
        topItem.selectedProperty().addListener((observableValue, before, after) -> {
            // 获取窗口容器
            Stage primaryStage = (Stage) this.getScene().getWindow();
            // 设置窗口为置顶
            primaryStage.setAlwaysOnTop(after);
        });
        englishItem.setOnAction(this::toggleLanguage);
        chineseItem.setOnAction(this::toggleLanguage);
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
     * 根据当前选中tab，更新菜单选项
     */
    public void updateMenuStatusBySelectedTab() {
        CenterTab selectedTab = centerTabPane.getSelected();
        lineFeedItem.selectedProperty().setValue(selectedTab.isAutoLine());
    }
}
