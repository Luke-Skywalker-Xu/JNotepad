package org.jcnc.jnotepad.ui.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.config.AppConfig;
import org.jcnc.jnotepad.app.i18n.UIResourceBundle;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.event.handler.*;
import org.jcnc.jnotepad.controller.i18n.LocalizationController;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;
import org.slf4j.Logger;

import java.util.*;

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

    LocalizationController localizationController = LocalizationController.getLocalizationConfig();
    AppConfigController appConfigController = AppConfigController.getInstance();
    Logger logger = LogUtil.getLogger(this.getClass());

    private JNotepadMenuBar() {
        initMenuBar();
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
    private RadioMenuItem chineseItem;
    /**
     * 英文选项
     */
    private RadioMenuItem englishItem;
    private final Map<String, MenuItem> itemMap = new HashMap<>();

    /**
     * 设置当前语言选中状态
     *
     * @param language 语言
     * @since 2023/8/25 22:49
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
        List<AppConfig.ShortcutKey> shortcutKeyConfigs = appConfigController.getShortcutKey();
        //FIXME: 如果shortcutKey不存在，需要解绑已绑定的快捷键
        for (AppConfig.ShortcutKey shortcutKey : shortcutKeyConfigs) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = this.itemMap.get(shortcutKey.getButtonName());
            if (Objects.isNull(menuItem)) {
                continue;
            }
            String shortKeyValue = shortcutKey.getShortcutKeyValue();
            if ("".equals(shortKeyValue)) {
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
        UIResourceBundle.bindStringProperty(languageMenu.textProperty(), LANGUAGE);
        ToggleGroup languageToggleGroup = new ToggleGroup();

        chineseItem = new RadioMenuItem();
        UIResourceBundle.bindStringProperty(chineseItem.textProperty(), UPPER_CHINESE);
        chineseItem.setUserData(Locale.CHINESE);
        itemMap.put("chineseItem", chineseItem);
        languageToggleGroup.getToggles().add(chineseItem);

        englishItem = new RadioMenuItem();
        UIResourceBundle.bindStringProperty(englishItem.textProperty(), UPPER_ENGLISH);
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
        UIResourceBundle.bindStringProperty(fileMenu.textProperty(), FILE);

        newItem = new MenuItem();
        UIResourceBundle.bindStringProperty(newItem.textProperty(), NEW);

        itemMap.put("newItem", newItem);

        openItem = new MenuItem();
        UIResourceBundle.bindStringProperty(openItem.textProperty(), OPEN);
        itemMap.put("openItem", openItem);

        saveItem = new MenuItem();
        UIResourceBundle.bindStringProperty(saveItem.textProperty(), SAVE);
        itemMap.put("saveItem", saveItem);

        saveAsItem = new MenuItem();
        UIResourceBundle.bindStringProperty(saveAsItem.textProperty(), SAVE_AS);
        itemMap.put("saveAsItem", saveAsItem);

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem);
    }

    /**
     * 初始化设置菜单
     */
    private void initSettingMenu() {
        logger.info("初始化设置菜单");
        // 设置菜单
        setMenu = new Menu();
        UIResourceBundle.bindStringProperty(setMenu.textProperty(), SET);

        lineFeedItem = new CheckMenuItem();
        UIResourceBundle.bindStringProperty(lineFeedItem.textProperty(), WORD_WRAP);
        itemMap.put("lineFeedItem", lineFeedItem);
        lineFeedItem.selectedProperty().set(true);

        topItem = new CheckMenuItem();
        UIResourceBundle.bindStringProperty(topItem.textProperty(), TOP);
        itemMap.put("topItem", topItem);

        openConfigItem = new MenuItem();
        UIResourceBundle.bindStringProperty(openConfigItem.textProperty(), OPEN_CONFIGURATION_FILE);
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
        UIResourceBundle.bindStringProperty(pluginMenu.textProperty(), PLUGIN);

        addItem = new MenuItem();
        UIResourceBundle.bindStringProperty(addItem.textProperty(), ADD_PLUGIN);
        itemMap.put("addItem", addItem);

        countItem = new MenuItem();
        UIResourceBundle.bindStringProperty(countItem.textProperty(), STATISTICS);
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
            AppConfigController.getInstance().setAutoLineConfig(after);
            // 2. 对当前tab生效配置
            jNotepadTabPane.fireTabSelected();
        });
        topItem.selectedProperty().addListener((observableValue, before, after) -> {
            // 获取窗口容器
            Stage primaryStage = (Stage) this.getScene().getWindow();
            // 设置窗口为置顶
            primaryStage.setAlwaysOnTop(after);
        });
        englishItem.setOnAction(actionEvent -> {
            setCurrentLanguage(ENGLISH);
            toggleLanguage(actionEvent);
        });
        chineseItem.setOnAction(actionEvent -> {
            setCurrentLanguage(CHINESE);
            toggleLanguage(actionEvent);
        });
    }

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
     * 设置当前语言<br>
     *
     * @param language 要设置的语言
     * @since 2023/8/26 16:16
     */
    private void setCurrentLanguage(String language) {
        // 如果当前已是该语言则不执行该方法
        if (localizationController.getLanguage().equals(language)) {
            return;
        }
        LocalizationController.setCurrentLanguage(language);
        AppConfigController.getInstance().updateLanguage(language);
    }

    /**
     * 根据当前选中tab，更新菜单选项
     */
    public void updateMenuStatusBySelectedTab() {
        JNotepadTab selectedTab = jNotepadTabPane.getSelected();
        lineFeedItem.selectedProperty().setValue(selectedTab.isAutoLine());
    }
}
