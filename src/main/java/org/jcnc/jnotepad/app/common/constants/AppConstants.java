package org.jcnc.jnotepad.app.common.constants;

import org.jcnc.jnotepad.app.i18n.UiResourceBundle;

import java.util.regex.Pattern;

/**
 * 应用常量类，用于存放应用程序中的常量值。
 *
 * <p>
 * 该类包含了应用程序的一些常用常量，如版本号、作者、软件名称、初始宽度和高度、Logo地址等。
 * </p>
 *
 * <p>
 * 还包括了与标签页相关的正则表达式模式，以及默认的属性和程序文件目录等常量。
 * </p>
 *
 * @author luke
 */
public class AppConstants {
    /**
     * 版本号
     */
    public static final String VERSION = "1.0.14-alpha";
    /**
     * 作者
     */
    public static final String AUTHOR = "JCNC";
    /**
     * 软件名称
     */
    public static final String APP_NAME = "JNotepad";
    /**
     * 初始宽度
     */
    public static final double SCREEN_WIDTH = 1050;
    /**
     * 初始高度
     */
    public static final double SCREEN_LENGTH = 750;
    /**
     * logo地址
     */
    public static final String APP_ICON = "/jcnc/app/images/appIcons/icon.png";

    /**
     * 默认标签页的正则
     *
     * <p>
     * 用于匹配默认标签页名称的正则表达式，格式为"New File"后跟数字。
     * </p>
     */
    public static final Pattern TABNAME_PATTERN = Pattern.compile("^" + Pattern.quote(UiResourceBundle.getContent(TextConstants.NEW_FILE)) + "\\d+$");

    /**
     * 默认属性
     */
    public static final String DEFAULT_PROPERTY = "user.home";
    /**
     * 程序文件目录
     */
    public static final String PROGRAM_FILE_DIRECTORY = ".jnotepad";

    /**
     * 私有构造函数，防止该类被实例化。
     */
    private AppConstants() {
    }
}
