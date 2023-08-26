package org.jcnc.jnotepad.ui.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jcnc.jnotepad.app.config.GlobalConfig;
import org.jcnc.jnotepad.app.config.LocalizationConfig;
import org.jcnc.jnotepad.controller.event.handler.*;
import org.jcnc.jnotepad.tool.LogUtil;
import org.jcnc.jnotepad.ui.status.JNotepadStatusBox;
import org.jcnc.jnotepad.ui.tab.JNotepadTab;
import org.jcnc.jnotepad.ui.tab.JNotepadTabPane;
import org.json.JSONObject;
import org.slf4j.Logger;

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

    JNotepadStatusBox jNotepadStatusBox = JNotepadStatusBox.getInstance();

    private static final JNotepadMenuBar MENU_BAR = new JNotepadMenuBar();

    LocalizationConfig localizationConfig = LocalizationConfig.getLocalizationConfig();
    Logger logger = LogUtil.getLogger(this.getClass());

    private JNotepadMenuBar() {

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
            default -> {

            }
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
    }

    /**
     * 初始化语言菜单
     */
    private void initLanguageMenu() {
        logger.info("初始化语言菜单:{}", localizationConfig.getLanguage());
        // 语言菜单
        languageMenu = new Menu(localizationConfig.getLanguage());
        ToggleGroup languageToggleGroup = new ToggleGroup();

        chineseItem = new RadioMenuItem(localizationConfig.getChinese());
        itemMap.put("chineseItem", chineseItem);
        languageToggleGroup.getToggles().add(chineseItem);

        englishItem = new RadioMenuItem(localizationConfig.getEnglish());
        itemMap.put("englishItem", englishItem);
        languageToggleGroup.getToggles().add(englishItem);

        languageMenu.getItems().addAll(chineseItem, englishItem);

    }

    /**
     * 初始化文件菜单
     */
    private void initFileMenu() {
        logger.info("初始化文件菜单:{}", localizationConfig.getFile());
        // 文件菜单
        fileMenu = new Menu(localizationConfig.getFile());

        newItem = new MenuItem(localizationConfig.getNewly());
        itemMap.put("newItem", newItem);

        openItem = new MenuItem(localizationConfig.getOpen());
        itemMap.put("openItem", openItem);

        saveItem = new MenuItem(localizationConfig.getSava());
        itemMap.put("saveItem", saveItem);

        saveAsItem = new MenuItem(localizationConfig.getSavaAs());
        itemMap.put("saveAsItem", saveAsItem);

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem);
    }

    /**
     * 初始化设置菜单
     */
    private void initSettingMenu() {
        logger.info("初始化设置菜单:{}", localizationConfig.getSet());
        // 设置菜单
        setMenu = new Menu(localizationConfig.getSet());

        lineFeedItem = new CheckMenuItem(localizationConfig.getWordWrap());
        itemMap.put("lineFeedItem", lineFeedItem);
        lineFeedItem.selectedProperty().set(true);

        topItem = new CheckMenuItem(localizationConfig.getTop());
        itemMap.put("topItem", topItem);

        openConfigItem = new MenuItem(localizationConfig.getOpenConfigurationFile());
        itemMap.put("openConfigItem", openConfigItem);

        itemMap.put("languageMenu", languageMenu);
        setMenu.getItems().addAll(lineFeedItem, openConfigItem, topItem, languageMenu);
    }

    /**
     * 初始化插件菜单
     */
    private void initPluginMenu() {
        logger.info("初始化插件菜单:{}", localizationConfig.getPlugin());
        // 插件菜单
        pluginMenu = new Menu(localizationConfig.getPlugin());
        addItem = new MenuItem(localizationConfig.getAddPlugin());
        itemMap.put("addItem", addItem);

        countItem = new MenuItem(localizationConfig.getStatistics());
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
        // todo 切换语言并将语言修改设置回本地,
        //  1.只更新json的language为english,没有保存
        englishItem.setOnAction(new OpenHandler() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // 获取当前的语言值
                JSONObject json = createShortcutKeyJson();

                // 更新语言值为 "english"
                json.put("language", "english");

                // 打印更新后的配置
                System.out.println(json.toString(4)); // 使用四个空格缩进

            }
        });
        chineseItem.setOnAction(new OpenHandler() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
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
