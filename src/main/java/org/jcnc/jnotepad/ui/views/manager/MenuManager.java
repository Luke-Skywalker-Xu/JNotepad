package org.jcnc.jnotepad.ui.views.manager;

import javafx.scene.control.Menu;
import org.jcnc.jnotepad.api.core.views.menu.builder.MenuBuilder;
import org.jcnc.jnotepad.app.utils.ClipboardUtil;
import org.jcnc.jnotepad.app.utils.FileUtil;
import org.jcnc.jnotepad.app.utils.NotificationUtil;

import java.io.File;

import static org.jcnc.jnotepad.app.common.constants.TextConstants.*;

/**
 * 菜单管理类
 *
 * @author gewuyou
 */
public class MenuManager {

    private MenuManager() {

    }

    /**
     * Generates a copy menu for the given file.
     *
     * @param file the file to generate the copy menu for
     * @return the generated copy menu
     */
    public static Menu getCopyMenu(File file) {
        return new MenuBuilder(COPY)
                .addMenuItem(FILE_NAME, e -> {
                    ClipboardUtil.writeTextToClipboard(file.getName());
                    NotificationUtil.infoNotification("已复制文件名!");
                })
                .addMenuItem(FILE_PATH, e -> {
                    ClipboardUtil.writeTextToClipboard(file.getAbsolutePath());
                    NotificationUtil.infoNotification("已复制文件路径!");
                })
                .addMenuItem(FOLDER_PATH, e -> {
                    ClipboardUtil.writeTextToClipboard(file.getParent());
                    NotificationUtil.infoNotification("已复制所在文件夹!");
                })
                .build();
    }

    /**
     * Retrieves the open menu for a given file.
     *
     * @param file the file for which to retrieve the open menu
     * @return the open menu for the given file
     */
    public static Menu getOpenMenu(File file) {
        return new MenuBuilder(OPEN_ON)
                .addMenuItem(EXPLORER, e -> FileUtil.openExplorer(file))
                .addMenuItem(TERMINAL, e -> FileUtil.openTerminal(file))
                .build();
    }

    /**
     * Returns a new Menu.
     *
     * @return a new Menu object
     */
    public static Menu getNewMenu() {
        return new MenuBuilder(NEW)
                .addMenuItem(FILE, e -> {
                })
                .addMenuItem(FOLDER, e -> {
                })
                .build();
    }

}
