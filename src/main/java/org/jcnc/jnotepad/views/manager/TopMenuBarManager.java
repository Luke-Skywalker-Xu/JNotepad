package org.jcnc.jnotepad.views.manager;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jcnc.jnotepad.api.core.views.manager.AbstractManager;
import org.jcnc.jnotepad.cache.entity.ShortcutKey;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.util.LogUtil;
import org.jcnc.jnotepad.views.root.top.menubar.TopMenuBar;
import org.jcnc.jnotepad.views.root.top.menubar.menu.*;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶部菜单栏管理类
 *
 * @author gewuyou
 */
public class TopMenuBarManager extends AbstractManager<Menu> {
    private static final TopMenuBarManager INSTANCE = new TopMenuBarManager();
    private final TopMenuBar topMenuBar = TopMenuBar.getInstance();
    private final List<Menu> nodeList = new ArrayList<>();
    Logger logger = LogUtil.getLogger(this.getClass());
    UserConfigController userConfigController = UserConfigController.getInstance();

    FileTopMenu fileTopMenu = FileTopMenu.getInstance();
    LanguageTopMenu languageTopMenu = LanguageTopMenu.getInstance();

    SettingTopMenu settingTopMenu = SettingTopMenu.getInstance();
    HelpTopMenu helpTopMenu = HelpTopMenu.getInstance();

    PluginTopMenu pluginTopMenu = PluginTopMenu.getInstance();
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
        fileTopMenu.initMenu();
        // 初始化语言菜单
        languageTopMenu.initMenu();
        // 初始化设置菜单
        settingTopMenu.initMenu();
        // 初始化帮助菜单
        helpTopMenu.initMenu();
        // 初始化插件菜单
        pluginTopMenu.initMenu();

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
        registerNode(fileTopMenu.getMenu());
        // 设置菜单
        registerNode(topMenuBar.getSetMenu());
        // 帮助菜单
        registerNode(helpTopMenu.getMenu());
        // 插件菜单
        registerNode(topMenuBar.getPluginMenu());
    }


    /**
     * 初始化快捷键
     */
    public void initShortcutKeys() {
        List<MenuItem> itemsToUnbind = new ArrayList<>();
        List<ShortcutKey> shortcutKeyConfigs = userConfigController.getShortcutKey();
        for (ShortcutKey shortcutKey : shortcutKeyConfigs) {
            // 保证json的key必须和变量名一致
            MenuItem menuItem = topMenuBar.getAllItemMap().get(shortcutKey.getButtonName());
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
     * 刷新顶部菜单
     */
    private void refreshTopMenuBar() {
        ObservableList<Menu> menus = topMenuBar.getMenus();
        menus.clear();
        menus.addAll(nodeList);
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
