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

    public static final String TITLE = "title";
    public static final String SAVE = "SAVE";
    public static final String FILE = "FILE";
    public static final String NEW = "NEW";
    public static final String OPEN = "OPEN";
    public static final String SAVE_AS = "SAVE_AS";
    public static final String SET = "SET";
    public static final String WORD_WRAP = "WORD_WRAP";
    public static final String PLUGIN = "PLUGIN";
    public static final String ADD_PLUGIN = "ADD_PLUGIN";
    public static final String STATISTICS = "STATISTICS";
    public static final String OPEN_CONFIGURATION_FILE = "OPEN_CONFIGURATION_FILE";
    public static final String TOP = "TOP";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String UPPER_CHINESE = "CHINESE";
    public static final String UPPER_ENGLISH = "ENGLISH";
    public static final String NEW_FILE = "NEW_FILE";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String ROW = "ROW";
    public static final String COLUMN = "COLUMN";
    public static final String WORD_COUNT = "WORD_COUNT";
    public static final String ENCODE = "ENCODE";

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
