package org.jcnc.jnotepad.views.root.top.menubar.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jcnc.jnotepad.api.core.views.top.menu.AbstractTopMenu;
import org.jcnc.jnotepad.component.stage.setting.plugin.PluginManagementPane;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.TextConstants.*;

/**
 * 插件菜单
 *
 * @author gewuyou
 */
public class PluginTopMenu extends AbstractTopMenu {

    private static final PluginTopMenu INSTANCE = new PluginTopMenu();

    private final Map<String, MenuItem> pluginMenuItems = new HashMap<>();

    public static PluginTopMenu getInstance() {
        return INSTANCE;
    }


    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return PLUGIN;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getPluginMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return pluginMenuItems;
    }

    /**
     * 注册顶部菜单
     */
    @Override
    protected void registerTopMenu() {
        registerMenuItem(topMenuBar.getPluginManagerItem(), MANAGER_PLUGIN, "pluginManagerItem", event -> new PluginManagementPane().run());
        registerMenuItem(topMenuBar.getCountItem(), STATISTICS, "countItem", event -> {
        });
    }
}
