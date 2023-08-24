package org.jcnc.jnotepad.constants;

import org.jcnc.jnotepad.init.Config;

import java.util.Properties;

/**
 * 文本常量
 *
 * @author gewuyou
 */
public class TextConstants {
    private TextConstants() {
    }

    private static final Config CONFIG = new Config();
    private static final Properties PROPERTIES = CONFIG.readPropertiesFromFile();

    ///菜单栏文本常量

    public static final String SAVA = PROPERTIES.getProperty("SAVA");

    public static final String FILE = PROPERTIES.getProperty("FILE");

    public static final String NEW = PROPERTIES.getProperty("NEW");

    public static final String OPEN = PROPERTIES.getProperty("OPEN");

    public static final String SAVA_AS = PROPERTIES.getProperty("SAVA_AS");

    public static final String SET = PROPERTIES.getProperty("SET");

    public static final String WORD_WRAP = PROPERTIES.getProperty("WORD_WRAP");

    public static final String PLUGIN = PROPERTIES.getProperty("PLUGIN");

    public static final String ADD_PLUGIN = PROPERTIES.getProperty("ADD_PLUGIN");

    public static final String STATISTICS = PROPERTIES.getProperty("STATISTICS");

    public static final String OPEN_CONFIGURATION_FILE = PROPERTIES.getProperty("OPEN_CONFIGURATION_FILE");

    /// GlobalConfig文本常量
    /**
     * 自动换行配置key
     */
    public static final String TEXT_WRAP = "text.wrap";

    /// NewFile 文本常量

    public static final String NEW_FILE = PROPERTIES.getProperty("NEW_FILE");

    /// Config 文本常量

    public static final String JNOTEPAD_CH_LANGUAGE_PACK_NAME = PROPERTIES.getProperty("JNotepad ch_language_pack");
    public static final String JNOTEPAD_EN_LANGUAGE_PACK_NAME = PROPERTIES.getProperty("JNotepad en_language_pack");

    /// EncodingDetector 文本常量
    public static final String UNKNOWN = "UNKNOWN";

    /// JNotepadStatusBox
    public static final String ROW = PROPERTIES.getProperty("ROW");

    public static final String COLUMN = PROPERTIES.getProperty("COLUMN");

    public static final String WORD_COUNT = PROPERTIES.getProperty("WORD_COUNT");

    public static final String ENCODE = PROPERTIES.getProperty("ENCODE");

    /// 配置文件文本常量
    /**
     * 内置配置文件
     */
    public static final String JNOTEPAD_CONFIG =
            """
                    {
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
