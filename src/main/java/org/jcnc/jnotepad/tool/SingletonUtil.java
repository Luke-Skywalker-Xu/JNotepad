package org.jcnc.jnotepad.tool;


import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.controller.config.AppConfigController;
import org.jcnc.jnotepad.controller.manager.Controller;

/**
 * 单例工具<br>
 * 封装了除UI组件外项目中所有需要的单例组件，以减少单例带来的代码的复杂性
 *
 * @author gewuyou
 */
public class SingletonUtil {


    /**
     * 控制器类
     */
    private static final Controller CONTROLLER = Controller.getInstance();

    /**
     * 应用程序配置控制器
     */
    private static final AppConfigController APP_CONFIG_CONTROLLER = AppConfigController.getInstance();
    /**
     * UI资源绑定组件
     */
    private static final UiResourceBundle UI_RESOURCE_BUNDLE = UiResourceBundle.getInstance();

    private SingletonUtil() {
    }

    /**
     * 获取控制器类
     *
     * @return org.jcnc.jnotepad.controller.manager.Controller
     * @since 2023/8/30 13:25
     */
    public static Controller getController() {
        return CONTROLLER;
    }

    /**
     * 获取应用程序配置控制器
     *
     * @return org.jcnc.jnotepad.controller.config.AppConfigController
     * @since 2023/8/30 12:48
     */
    public static AppConfigController getAppConfigController() {
        return APP_CONFIG_CONTROLLER;
    }

    /**
     * 获取UI资源绑定组件I
     *
     * @return org.jcnc.jnotepad.app.i18n.UiResourceBundle
     * @since 2023/8/30 12:45
     */
    public static UiResourceBundle getUiResourceBundle() {
        return UI_RESOURCE_BUNDLE;
    }
}
