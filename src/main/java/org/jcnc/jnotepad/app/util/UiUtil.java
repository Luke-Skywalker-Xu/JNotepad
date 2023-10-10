package org.jcnc.jnotepad.app.util;

import atlantafx.base.theme.Styles;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import org.jcnc.jnotepad.app.common.constants.AppConstants;
import org.jcnc.jnotepad.app.manager.ApplicationManager;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.HashMap;
import java.util.Map;
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

    private static final FontIcon SUCCESS_ICON = FontIcon.of(CHECK_CIRCLE);
    /**
     * 图标集合
     */
    private static final Map<String, Node> ICON_MAP = new HashMap<>(32);

    static {
        // 暂时设置颜色
        ERROR_ICON.getStyleClass().addAll(Styles.DANGER);
        INFO_ICON.getStyleClass().addAll(Styles.ACCENT);
        QUESTION_ICON.getStyleClass().addAll(Styles.ACCENT);
        WARNING_ICON.getStyleClass().addAll(Styles.WARNING);
        SUCCESS_ICON.getStyleClass().addAll(Styles.SUCCESS);
        ICON_MAP.put("css", fileIconByPng("css"));
        ICON_MAP.put("doc", fileIconByPng("doc"));
        ICON_MAP.put("dll", fileIconByPng("dll"));
        ICON_MAP.put("exe", fileIconByPng("exe"));
        ICON_MAP.put("gif", fileIconByPng("gif"));
        ICON_MAP.put("gitignore", fileIconByPng("git"));
        ICON_MAP.put("html", fileIconByPng("html"));
        ICON_MAP.put("json", fileIconByPng("json"));
        ICON_MAP.put("md", fileIconByPng("markdown"));
        ICON_MAP.put("pdf", FontIcon.of(FILE_PDF));
        ICON_MAP.put("ppt", FontIcon.of(FILE_PPT));
        ICON_MAP.put("png", fileIconByPng("png"));
        ICON_MAP.put("sql", fileIconByPng("database"));
        ICON_MAP.put("svg", fileIconByPng("svg"));
        ICON_MAP.put("txt", FontIcon.of(FILE_TEXT));
        ICON_MAP.put("xls", FontIcon.of(FILE_EXCEL));
        ICON_MAP.put("xml", fileIconByPng("xml"));
        // 编程语言
        ICON_MAP.put("bat", fileIconByPng("bat"));
        ICON_MAP.put("c", fileIconByPng("c"));
        ICON_MAP.put("cs", fileIconByPng("csharp"));
        ICON_MAP.put("cpp", fileIconByPng("cplusplus"));
        ICON_MAP.put("go", fileIconByPng("golang"));
        ICON_MAP.put("js", fileIconByPng("js"));
        ICON_MAP.put("java", fileIconByPng("java"));
        ICON_MAP.put("kt", fileIconByPng("kotlin"));
        ICON_MAP.put("lua", fileIconByPng("lua"));
        ICON_MAP.put("py", fileIconByPng("python"));
        ICON_MAP.put("php", fileIconByPng("php"));
        ICON_MAP.put("rb", fileIconByPng("ruby"));
        ICON_MAP.put("sh", fileIconByPng("sh"));


    }

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
     * Retrieves the success icon.
     *
     * @return the success icon as a FontIcon object
     */
    public static FontIcon getSuccessIcon() {
        return SUCCESS_ICON;
    }

    /**
     * 获取应用窗口。
     *
     * @return javafx.stage.Window 应用窗口对象
     * @apiNote JnotepadApp.getWindow()
     */
    public static Window getAppWindow() {
        return ApplicationManager.getInstance().getWindow();
    }

    /**
     * Generates an ImageView with the specified module directory, name, and format.
     *
     * @param moduleDir the directory where the module is located
     * @param name      the name of the icon
     * @param format    the format of the icon
     * @return the generated ImageView
     */
    public static ImageView icon(String moduleDir, String name, String format) {
        return icon(moduleDir + name + format);
    }

    /**
     * Generates an ImageView object with the image specified by the given path.
     *
     * @param path the path to the image file
     * @return the ImageView object with the specified image
     */
    public static ImageView icon(String path) {
        var img = new Image(ResourceUtil.getResourceAsStream(path));
        return new ImageView(img);
    }

    /**
     * Generates an ImageView based on a PNG file.
     *
     * @param moduleDir the directory of the module
     * @param name      the name of the PNG file
     * @return the generated ImageView
     */
    public static ImageView iconByPng(String moduleDir, String name) {
        return icon(moduleDir + name + ".png");
    }

    /**
     * Generates an ImageView object for a file icon based on the given PNG name.
     *
     * @param name the name of the PNG file for the file icon
     * @return the ImageView object representing the file icon
     */
    public static ImageView fileIconByPng(String name) {
        return iconByPng("images/fileIcons/", name);
    }

    /**
     * Generates an ImageView object for a file icon based on the given PNG name.
     *
     * @param name the name of the PNG file for the file icon
     * @return the ImageView object representing the file icon
     */
    public static ImageView sidebarIconByPng(String name) {
        return iconByPng("images/sidebarIcons/", name);
    }

    public static Map<String, Node> getIconMap() {
        return ICON_MAP;
    }
}
