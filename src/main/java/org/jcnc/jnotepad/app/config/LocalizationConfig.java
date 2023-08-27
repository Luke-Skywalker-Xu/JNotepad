package org.jcnc.jnotepad.app.config;

import org.jcnc.jnotepad.LunchApp;
import org.jcnc.jnotepad.app.i18n.UIResourceBundle;
import org.jcnc.jnotepad.tool.LogUtil;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.jcnc.jnotepad.constants.AppConstants.APP_NAME;
import static org.jcnc.jnotepad.constants.TextConstants.*;

/**
 * 本地化配置文件<br>
 * 注意：该配置文件必须先于快捷键配置文件加载
 *
 * @author gewuyou
 * @see LunchApp
 */
public class LocalizationConfig {
    private static final LocalizationConfig LOCALIZATION_CONFIG = new LocalizationConfig();
    private final Properties properties = new Properties();

    private String language;

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

    public static final Locale getCurrentLocal() {
        return Locale.getDefault();
    }

    public static final void setCurrentLocal(Locale locale) {
        Locale.setDefault(locale);
        UIResourceBundle.getInstance().resetLocal();
        LOCALIZATION_CONFIG.setLanguage(SUPPORT_LANGUAGES.get(locale));
    }

    public static final void setCurrentLocal(String language) {
        Locale locale = SUPPORT_LOCALES.get(language);
        if (locale != null) {
            setCurrentLocal(locale);
        }
    }

    private LocalizationConfig() {

    }

    private String textWrap;


    public static LocalizationConfig getLocalizationConfig() {
        return LOCALIZATION_CONFIG;
    }


    public String getTextWrap() {
        return textWrap;
    }

    public void setTextWrap(String textWrap) {
        this.textWrap = textWrap;
        properties.setProperty(TEXT_WRAP, textWrap);
    }

    private void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return this.language;
    }
}
