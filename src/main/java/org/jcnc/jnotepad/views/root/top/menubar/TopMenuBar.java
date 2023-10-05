package org.jcnc.jnotepad.views.root.top.menubar;

import javafx.scene.control.*;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;
import org.jcnc.jnotepad.views.root.center.main.center.tab.CenterTab;

import java.util.HashMap;
import java.util.Map;

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
    private final Map<String, MenuItem> allItemMap = new HashMap<>();
    /**
     * 标签页布局组件封装。
     */
    CenterTabPaneManager centerTabPane = CenterTabPaneManager.getInstance();
    /**
     * 文件菜单
     */
    private final Menu fileMenu = new Menu();
    /**
     * 设置菜单
     */
    private final Menu setMenu = new Menu();

    /**
     * 帮助菜单
     */
    private final Menu helpMenu = new Menu();
    ///  菜单按钮
    /**
     * 插件菜单
     */
    private final Menu pluginMenu = new Menu();
    private final Menu runMenu = new Menu();

    /**
     * 语言菜单
     */
    private final Menu languageMenu = new Menu();
    /**
     * 新建
     */
    private final MenuItem newItem = new MenuItem();

    /**
     * 新建
     */
    private final MenuItem aboutItem = new MenuItem();
    /**
     * 打开
     */
    private final MenuItem openItem = new MenuItem();
    /**
     * 打开文件夾
     */
    private final MenuItem openDirItem = new MenuItem();
    /**
     * 另存为
     */
    private final MenuItem saveAsItem = new MenuItem();
    /**
     * 保存
     */
    private final MenuItem saveItem = new MenuItem();
    /**
     * 重命名
     */
    private final MenuItem renameItem = new MenuItem();

    /**
     * 查看
     */
    private final MenuItem countItem = new MenuItem();
    /**
     * 打开配置文件
     */
    private final MenuItem openConfigItem = new MenuItem();
    /**
     * 自动换行点击菜单按钮
     */
    private final CheckMenuItem lineFeedItem = new CheckMenuItem();
    /**
     * 置顶按钮
     */
    private final CheckMenuItem topItem = new CheckMenuItem();
    /**
     * 中文选项
     */
    private final RadioMenuItem chineseItem = new RadioMenuItem();
    /**
     * 英文选项
     */
    private final RadioMenuItem englishItem = new RadioMenuItem();

    /**
     * 插件管理菜单项
     */
    private final MenuItem pluginManagerItem = new MenuItem();

    private TopMenuBar() {
    }

    public static TopMenuBar getInstance() {
        return MENU_BAR;
    }


    /**
     * 根据当前选中tab，更新菜单选项
     */
    public void updateMenuStatusBySelectedTab() {
        CenterTab selectedTab = centerTabPane.getSelected();
        lineFeedItem.selectedProperty().setValue(selectedTab.isAutoLine());
    }

    public Menu getFileMenu() {
        return fileMenu;
    }

    public Menu getSetMenu() {
        return setMenu;
    }

    public Menu getHelpMenu() {
        return helpMenu;
    }



    public Menu getPluginMenu() {
        return pluginMenu;
    }
    public Menu getRunMenu() {
        return runMenu;
    }



    public Menu getLanguageMenu() {
        return languageMenu;
    }

    public Map<String, MenuItem> getAllItemMap() {
        return allItemMap;
    }

    public MenuItem getNewItem() {
        return newItem;
    }


    public MenuItem getAboutItem() {
        return aboutItem;
    }

    public MenuItem getOpenItem() {
        return openItem;
    }

    public MenuItem getSaveAsItem() {
        return saveAsItem;
    }

    public MenuItem getSaveItem() {
        return saveItem;
    }

    public MenuItem getRenameItem() {
        return renameItem;
    }


    public MenuItem getCountItem() {
        return countItem;
    }

    public MenuItem getOpenConfigItem() {
        return openConfigItem;
    }

    public CheckMenuItem getLineFeedItem() {
        return lineFeedItem;
    }

    public CheckMenuItem getTopItem() {
        return topItem;
    }

    public RadioMenuItem getChineseItem() {
        return chineseItem;
    }

    public RadioMenuItem getEnglishItem() {
        return englishItem;
    }

    public MenuItem getPluginManagerItem() {
        return pluginManagerItem;
    }

    public MenuItem getOpenDirItem() {
        return openDirItem;
    }
}
