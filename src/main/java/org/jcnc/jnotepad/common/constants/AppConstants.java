package org.jcnc.jnotepad.common.constants;

import org.jcnc.jnotepad.app.i18n.UiResourceBundle;

import java.util.regex.Pattern;

/**
 * 应用常量类，用于存放应用程序中的常量值。
 *
 * @author 许轲
 */
public class AppConstants {
    /**
     * 初始宽度
     */
    public static final double SCREEN_WIDTH = 800;
    /**
     * 初始高度
     */
    public static final double SCREEN_LENGTH = 600;
    /**
     * logo地址
     */
    public static final String APP_ICON = "/img/icon.png";

    /**
     * 默认标签页的正则
     */
    public static final Pattern TABNAME_PATTERN = Pattern.compile("^" + Pattern.quote(UiResourceBundle.getContent(TextConstants.NEW_FILE)) + "\\d+$");

    /**
     * 默认属性
     */
    public static final String DEFAULT_PROPERTY = "user.home";


    /**
     * 私有构造函数，防止该类被实例化。
     */
    private AppConstants() {
    }

}
