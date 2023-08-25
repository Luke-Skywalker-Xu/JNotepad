package org.jcnc.jnotepad.constants;


import java.util.Map;

/**
 * 文本常量
 * <p>
 * 任何string请都在此处readPropertiesFromFile,然后在
 * src/main/java/org/jcnc/jnotepad/init/Config.java的getXXXXXLanguagePack
 * 注册配置文件,设置多语言语言包
 *
 * @author gewuyou
 */
public class TextConstants {

    private TextConstants() {
    }


    /// GlobalConfig文本常量
    /**
     * 自动换行配置key
     */
    public static final String TEXT_WRAP = "text.wrap";


    /// Config 文本常量

    public static final String CH_LANGUAGE_PACK_NAME = "ch_language_pack.txt";
    public static final String EN_LANGUAGE_PACK_NAME = "en_language_pack.txt";

    public static final String ENGLISH = "english";

    public static final String CHINESE = "chinese";

    /// 语言映射
    public static final Map<String, String> LANGUAGE_FILE_MAP = Map.of(
            CHINESE, CH_LANGUAGE_PACK_NAME,
            ENGLISH, EN_LANGUAGE_PACK_NAME
    );
    /// 配置文件文本常量
    /**
     * 内置配置文件
     */
    public static final String JNOTEPAD_CONFIG =
            """
                    {
                               "language":"chinese",
                               "shortcutKey":[
                                 {
                                       "buttonName": "newItem",
                                       "shortcutKeyValue": "ctrl+n"
                                 },
                                 {
                                       "buttonName": "openItem",
                                       "shortcutKeyValue": "ctrl+o"
                                 },
                                 {
                                      "buttonName": "saveItem",
                                      "shortcutKeyValue": "ctrl+s"
                                 },
                                 {
                                       "buttonName": "saveAsItem",
                                       "shortcutKeyValue": "ctrl+alt+s"
                                 },
                                 {
                                       "buttonName": "lineFeedItem",
                                       "shortcutKeyValue": ""
                                 },
                                 {
                                       "buttonName": "openConfigItem",
                                       "shortcutKeyValue": "alt+s"
                                 },
                                 {
                                       "buttonName": "addItem",
                                       "shortcutKeyValue": ""
                                 },
                                 {
                                       "buttonName": "countItem",
                                       "shortcutKeyValue": ""
                                 }
                               ]
                    }
                    """;
}
