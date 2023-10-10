package org.jcnc.jnotepad.ui.views.root.top.menubar.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jcnc.jnotepad.api.core.views.top.menu.AbstractTopMenu;
import org.jcnc.jnotepad.controller.event.handler.menuitem.*;
import org.jcnc.jnotepad.controller.event.handler.toolbar.OpenDirectory;

import java.util.HashMap;
import java.util.Map;

import static org.jcnc.jnotepad.app.common.constants.TextConstants.*;

/**
 * 文件菜单
 *
 * @author gewuyou
 */
public class FileTopMenu extends AbstractTopMenu {
    private static final FileTopMenu INSTANCE = new FileTopMenu();
    private final Map<String, MenuItem> fileMenuItems = new HashMap<>();

    public static FileTopMenu getInstance() {
        return INSTANCE;
    }

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @Override
    public String getMenuName() {
        return FILE;
    }

    /**
     * 获取菜单
     *
     * @return 菜单
     */
    @Override
    public Menu getMenu() {
        return topMenuBar.getFileMenu();
    }

    /**
     * 获取菜单项集合
     *
     * @return 菜单项集合
     */
    @Override
    public Map<String, MenuItem> getMenuItems() {
        return fileMenuItems;
    }


    /**
     * 注册顶部菜单
     */
    @Override
    protected void registerTopMenu() {
        // 文件菜单
        registerMenuItem(topMenuBar.getNewItem(), NEW, "newItem", new NewFile());
        registerMenuItem(topMenuBar.getOpenItem(), OPEN, "openItem", new OpenFile());
        registerMenuItem(topMenuBar.getSaveItem(), SAVE, "saveItem", new SaveFile());
        registerMenuItem(topMenuBar.getSaveAsItem(), SAVE_AS, "saveAsItem", new SaveAsFile());
        registerMenuItem(topMenuBar.getRenameItem(), RENAME, "renameItem", new RenameFile());
        // 打开文件夹按钮
        registerMenuItem(topMenuBar.getOpenDirItem(), OPEN_DIRECTORY, "openDirItem", new OpenDirectory());
    }
}
