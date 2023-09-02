package org.jcnc.jnotepad.tool;

import javafx.scene.image.Image;
import javafx.stage.Window;
import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.constants.AppConstants;

import java.util.Objects;

/**
 * UI工具
 *
 * <p>封装了项目中所有的UI组件，以减少组件单例模式造成的代码复杂性。</p>
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
     * 获取应用程序图标。
     *
     * @return javafx.scene.image.Image 应用程序图标对象
     */
    public static Image getIcon() {
        return ICON;
    }

    /**
     * 获取应用窗口。
     *
     * @return javafx.stage.Window 应用窗口对象
     * @apiNote JNotepadTabPane.getInstance().getSelected().getTabPane().getScene().getWindow()
     */
    public static Window getAppWindow() {
        return LunchApp.getWindow();
    }


}
