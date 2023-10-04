package org.jcnc.jnotepad.views.root.top.menubar.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.jcnc.jnotepad.api.core.views.top.menu.AbstractTopMenu;
import org.jcnc.jnotepad.api.util.UiUtil;
import org.jcnc.jnotepad.controller.config.UserConfigController;
import org.jcnc.jnotepad.controller.event.handler.menuitem.OpenConfig;
import org.jcnc.jnotepad.views.manager.CenterTabPaneManager;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.TextConstants.*;

/**
 * 设置菜单
 *
 * @author gewuyou
 */
public class SettingTopMenu extends AbstractTopMenu {

    private static final SettingTopMenu INSTANCE = new SettingTopMenu();
    private final Map<String, MenuItem> setMenuItems = new HashMap<>();

    public static SettingTopMenu getInstance() {
        return INSTANCE;
    }

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return SET;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getSetMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return setMenuItems;
    }

    /**
     * 注册顶部菜单
     *
     * @apiNote 此方法
     */
    @Override
    protected void registerTopMenu() {
        registerMenuItem(topMenuBar.getLineFeedItem(), WORD_WRAP, "lineFeedItem", (observableValue, before, after) -> {
            // 1. 更新全局配置
            UserConfigController.getInstance().setAutoLineConfig(after);
            // 2. 对当前tab生效配置
            CenterTabPaneManager.getInstance().fireTabSelected();
        });
        topMenuBar.getLineFeedItem().selectedProperty().set(true);

        registerMenuItem(topMenuBar.getTopItem(), TOP, "topItem", (observableValue, before, after) -> {
            // 获取窗口容器
            Stage primaryStage = (Stage) UiUtil.getAppWindow();
            // 设置窗口为置顶
            primaryStage.setAlwaysOnTop(after);
        });

        registerMenuItem(topMenuBar.getOpenConfigItem(), OPEN_CONFIGURATION_FILE, "openConfigItem", new OpenConfig());
        registerMenuItem(topMenuBar.getLanguageMenu(), LANGUAGE, "languageMenu", actionEvent -> {
        });
    }
}
