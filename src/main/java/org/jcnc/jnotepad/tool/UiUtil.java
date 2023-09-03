package org.jcnc.jnotepad.tool;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.constants.AppConstants;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

import static org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled.*;

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
    private static final Image APP_ICON = new Image(Objects.requireNonNull(UiUtil.class.getResource(AppConstants.APP_ICON)).toString());

    /**
     * 错误图标
     */
    private static final FontIcon ERROR_ICON = FontIcon.of(EXCLAMATION_CIRCLE, 64, Color.RED);

    /**
     * 信息图标
     */
    private static final FontIcon INFO_ICON = FontIcon.of(INFO_CIRCLE, 64, Color.BLUE);
    /**
     * 警告图标
     */
    private static final FontIcon WARNING_ICON = FontIcon.of(WARNING, 64, Color.ORANGE);
    /**
     * 问题图标
     */
    private static final FontIcon QUESTION_ICON = FontIcon.of(QUESTION_CIRCLE, 64, Color.YELLOW);

    private UiUtil() {
    }


    /**
     * 获取应用程序图标。
     *
     * @return javafx.scene.image.Image 应用程序图标对象
     */
    public static Image getAppIcon() {
        return APP_ICON;
    }

    /**
     * Retrieves the information icon.
     *
     * @return the information icon
     */

    public static FontIcon getInfoIcon() {
        return INFO_ICON;
    }

    /**
     * Returns the error icon.
     *
     * @return the error icon
     */
    public static FontIcon getErrorIcon() {
        return ERROR_ICON;
    }

    /**
     * Retrieves the warning icon.
     *
     * @return the warning icon
     */
    public static FontIcon getWarningIcon() {
        return WARNING_ICON;
    }

    /**
     * Retrieves the question icon.
     *
     * @return the question icon
     */
    public static FontIcon getQuestionIcon() {
        return QUESTION_ICON;
    }

    /**
     * 获取应用窗口。
     *
     * @return javafx.stage.Window 应用窗口对象
     * @apiNote LunchApp.getWindow()
     */
    public static Window getAppWindow() {
        return LunchApp.getWindow();
    }


}
