package org.jcnc.jnotepad.controller.i18n;

import org.jcnc.jnotepad.JnotepadApp;
import org.jcnc.jnotepad.app.i18n.UiResourceBundle;
import org.jcnc.jnotepad.controller.config.UserConfigController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static org.jcnc.jnotepad.common.constants.TextConstants.CHINESE;
import static org.jcnc.jnotepad.common.constants.TextConstants.ENGLISH;

/**
 * 本地化控制器
 *
 * <p>该类负责处理应用程序的本地化配置，包括语言和区域设置。</p>
 *
 * @author gewuyou
 * @see JnotepadApp
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

    private final UserConfigController userConfigController;

    private LocalizationController() {
        this.userConfigController = UserConfigController.getInstance();
    }

    /**
     * 获取当前语言配置
     *
     * @return 当前语言的Locale对象
     */
    public static Locale getCurrentLocal() {
        return Locale.getDefault();
    }

    /**
     * 设置当前语言配置
     *
     * @param locale 当前语言的Locale对象
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

        UiResourceBundle.getInstance().resetLocal(getCurrentLocal());
        LOCALIZATION_CONFIG.setLanguage(SUPPORT_LANGUAGES.get(locale));
    }

    /**
     * 初始化语言配置
     */
    public static void initLocal() {
        setCurrentLocal(null);
    }

    /**
     * 查询当前语言配置
     *
     * @return appConfig中的当前语言配置
     */
    public String getLanguage() {
        return userConfigController.getLanguage();
    }

    private void setLanguage(String language) {
        userConfigController.updateLanguage(language);
    }
}
