package org.jcnc.jnotepad.tool;

import javafx.scene.image.Image;
import javafx.stage.Window;
import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.constants.AppConstants;

import java.util.Objects;

/**
 * UI工具
 *
 * @author gewuyou
 */
public class UiUtil {
    /**
     * 应用程序图标
     */
    private static final Image ICON = new Image(Objects.requireNonNull(UiUtil.class.getResource(AppConstants.APP_ICON)).toString());

    private UiUtil() {
    }

    /**
     * 获取应用程序图标
     *
     * @return javafx.scene.image.Image
     * @since 2023/8/30 11:03
     */
    public static Image getIcon() {
        return ICON;
    }

    /**
     * 获取应用窗口
     *
     * @return javafx.stage.Window
     * @apiNote JNotepadTabPane.getInstance().getSelected().getTabPane().getScene().getWindow()
     * @since 2023/8/29 14:12
     */
    public static Window getAppWindow() {
        return LunchApp.getWindow();
    }
}
