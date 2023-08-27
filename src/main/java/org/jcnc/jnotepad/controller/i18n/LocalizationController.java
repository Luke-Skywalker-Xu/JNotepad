package org.jcnc.jnotepad.controller.i18n;

import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.app.i18n.UIResourceBundle;
import org.jcnc.jnotepad.controller.config.AppConfigController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static org.jcnc.jnotepad.constants.TextConstants.CHINESE;
import static org.jcnc.jnotepad.constants.TextConstants.ENGLISH;

/**
 * 本地化配置文件<br>
 * 注意：该配置文件必须先于快捷键配置文件加载
 *
 * @author gewuyou
 * @see LunchApp
 */
public class LocalizationController {
    private static final LocalizationController LOCALIZATION_CONFIG = new LocalizationController();

    private static final Map<String, Locale> SUPPORT_LOCALES;
    private static final Map<Locale, String> SUPPORT_LANGUAGES;

    static {
        Locale.setDefault(Locale.ENGLISH);
        SUPPORT_LOCALES = new LinkedHashMap<>();
        SUPPORT_LOCALES.put(CHINESE, Locale.CHINESE);
        SUPPORT_LOCALES.put(ENGLISH, Locale.ENGLISH);

        SUPPORT_LANGUAGES = new HashMap<>();
        SUPPORT_LANGUAGES.put(Locale.CHINESE, CHINESE);
        SUPPORT_LANGUAGES.put(Locale.ENGLISH, ENGLISH);
    }

    public static Locale getCurrentLocal() {
        return Locale.getDefault();
    }

    /**
     * 初始化语言配置
     */
    public static void initLocal() {
        setCurrentLocal(null);
    }

    /**
     * 设置当前语言配置
     *
     * @param locale 当前语言Local对象
     */
    public static void setCurrentLocal(Locale locale) {
        if (locale != null && locale.equals(getCurrentLocal())) {
            // 要更新的语言与当前语言一致，则不执行
            return;
        }
        if (locale == null) {
            locale = SUPPORT_LOCALES.get(LOCALIZATION_CONFIG.getLanguage());
        }
        if (locale == null) {
            locale = getCurrentLocal();
        }
        Locale.setDefault(locale);

        UIResourceBundle.getInstance().resetLocal(getCurrentLocal());
        LOCALIZATION_CONFIG.setLanguage(SUPPORT_LANGUAGES.get(locale));
    }

    private LocalizationController() {
        this.appConfigController = AppConfigController.getInstance();
    }

    private final AppConfigController appConfigController;


    private void setLanguage(String language) {
        appConfigController.updateLanguage(language);
    }

    /**
     * 查询当前语言配置
     *
     * @return appConfig中的当前语言配置
     */
    public String getLanguage() {
        return appConfigController.getLanguage();
    }
}
