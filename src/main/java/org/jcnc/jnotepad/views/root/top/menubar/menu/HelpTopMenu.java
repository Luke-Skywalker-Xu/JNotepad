package org.jcnc.jnotepad.views.root.top.menubar.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.jcnc.jnotepad.api.core.views.top.menu.AbstractTopMenu;
import org.jcnc.jnotepad.component.stage.setting.HelpPaneStage;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.TextConstants.ABOUT;
import static org.jcnc.jnotepad.common.constants.TextConstants.HELP;

/**
 * 帮助菜单
 *
 * @author gewuyou
 */
public class HelpTopMenu extends AbstractTopMenu {

    private static final HelpTopMenu INSTANCE = new HelpTopMenu();

    private final Map<String, MenuItem> helpMenuItems = new HashMap<>();

    public static HelpTopMenu getInstance() {
        return INSTANCE;
    }


    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return HELP;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getHelpMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return helpMenuItems;
    }

    /**
     * 注册顶部菜单
     */
    @Override
    protected void registerTopMenu() {
        registerMenuItem(topMenuBar.getAboutItem(), ABOUT, "aboutItem", event -> new HelpPaneStage().run(new Stage()));
    }
}
