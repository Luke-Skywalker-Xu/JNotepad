package org.jcnc.jnotepad.util;

import atlantafx.base.theme.Styles;
import javafx.scene.image.Image;
import javafx.stage.Window;
import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.common.constants.AppConstants;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

import static org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled.*;

/**
 * UI工具
 *
 * <p>封装了项目中需要引入的UI组件</p>
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
    private static final FontIcon ERROR_ICON = FontIcon.of(EXCLAMATION_CIRCLE);

    /**
     * 信息图标
     */
    private static final FontIcon INFO_ICON = FontIcon.of(INFO_CIRCLE);
    /**
     * 警告图标
     */
    private static final FontIcon WARNING_ICON = FontIcon.of(WARNING);
    /**
     * 问题图标
     */
    private static final FontIcon QUESTION_ICON = FontIcon.of(QUESTION_CIRCLE);

    private static final FontIcon SUCCEED_ICON = FontIcon.of(CHECK_CIRCLE);

    private UiUtil() {
    }

    static {
        // 暂时设置颜色
        ERROR_ICON.getStyleClass().addAll(Styles.DANGER);
        INFO_ICON.getStyleClass().addAll(Styles.ACCENT);
        QUESTION_ICON.getStyleClass().addAll(Styles.ACCENT);
        WARNING_ICON.getStyleClass().addAll(Styles.WARNING);
        SUCCEED_ICON.getStyleClass().addAll(Styles.SUCCESS);
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
