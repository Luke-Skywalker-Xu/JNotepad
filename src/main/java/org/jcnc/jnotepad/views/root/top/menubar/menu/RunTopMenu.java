package org.jcnc.jnotepad.views.root.top.menubar.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jcnc.jnotepad.api.core.views.top.menu.AbstractTopMenu;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.TextConstants.DE_BUG;
import static org.jcnc.jnotepad.common.constants.TextConstants.RUN;

/**
 * 文件菜单
 *
 * @author gewuyou
 */
public class RunTopMenu extends AbstractTopMenu {
    private static final RunTopMenu INSTANCE = new RunTopMenu();
    private final Map<String, MenuItem> runMenuItems = new HashMap<>();

    public static RunTopMenu getInstance() {
        return INSTANCE;
    }

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return RUN;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getRunMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return runMenuItems;
    }


    /**
     * 注册顶部菜单
     */
    @Override
    protected void registerTopMenu() {
        // 运行
        registerMenuItem(topMenuBar.getRunItem(), RUN, "runItem", null);

        // 调试
        registerMenuItem(topMenuBar.getDeBugItem(), DE_BUG, "deBugItem", null);


    }
}
