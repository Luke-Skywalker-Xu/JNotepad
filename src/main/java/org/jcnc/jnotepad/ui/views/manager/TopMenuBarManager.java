package org.jcnc.jnotepad.ui.views.manager;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import org.jcnc.jnotepad.api.core.views.manager.AbstractManager;
import org.jcnc.jnotepad.app.utils.LoggerUtil;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.ui.views.root.top.menubar.TopMenuBar;
import org.jcnc.jnotepad.ui.views.root.top.menubar.menu.*;
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
    Logger logger = LoggerUtil.getLogger(this.getClass());
    UserConfigController userConfigController = UserConfigController.getInstance();


    FileTopMenu fileTopMenu = FileTopMenu.getInstance();
    LanguageTopMenu languageTopMenu = LanguageTopMenu.getInstance();

    SettingTopMenu settingTopMenu = SettingTopMenu.getInstance();

    RunTopMenu runTopMenu = RunTopMenu.getInstance();
    PluginTopMenu pluginTopMenu = PluginTopMenu.getInstance();

    HelpTopMenu helpTopMenu = HelpTopMenu.getInstance();

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
        // 初始化运行菜单
        runTopMenu.initMenu();

        // 刷新顶部菜单栏
        refreshTopMenuBar();

    }

    /**
     * 注册顶部菜单栏
     */
    public void registerTopMenuBar() {
        // 文件菜单
        registerNode(fileTopMenu.getMenu());

        // 设置菜单
        registerNode(topMenuBar.getSetMenu());

        // 插件菜单
        registerNode(topMenuBar.getPluginMenu());

        // 运行菜单
        registerNode(runTopMenu.getMenu());

        // 帮助菜单
        registerNode(helpTopMenu.getMenu());
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
