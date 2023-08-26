package org.jcnc.jnotepad.constants;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jcnc.jnotepad.tool.JsonUtil;

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

    public static final String LOWER_LANGUAGE = "language";

    private static final String BUTTON_NAME = "buttonName";

    private static final String SHORTCUT_KEY_VALUE = "shortcutKeyValue";
    /**
     * 语言映射
     */
    public static final Map<String, String> LANGUAGE_FILE_MAP = Map.of(
            CHINESE, CH_LANGUAGE_PACK_NAME,
            ENGLISH, EN_LANGUAGE_PACK_NAME
    );
    /// 配置文件文本常量
    /**
     * 内置配置文件
     */
    public static final String JNOTEPAD_CONFIG = createShortcutKeyJsonString();

    private static String createShortcutKeyJsonString() {
        return JsonUtil.toJsonString(createShortcutKeyJson());
    }

    public static ObjectNode createShortcutKeyJson() {
        ObjectNode json = JsonUtil.OBJECT_MAPPER.createObjectNode();
        json.put(LOWER_LANGUAGE, CHINESE);

        ArrayNode shortcutKeyArray = json.putArray("shortcutKey");

        ObjectNode newItem = json.objectNode();
        newItem.put(BUTTON_NAME, "newItem");
        newItem.put(SHORTCUT_KEY_VALUE, "ctrl+n");
        shortcutKeyArray.add(newItem);

        ObjectNode openItem = json.objectNode();
        openItem.put(BUTTON_NAME, "openItem");
        openItem.put(SHORTCUT_KEY_VALUE, "ctrl+o");
        shortcutKeyArray.add(openItem);

        ObjectNode saveItem = json.objectNode();
        saveItem.put(BUTTON_NAME, "saveItem");
        saveItem.put(SHORTCUT_KEY_VALUE, "ctrl+s");
        shortcutKeyArray.add(saveItem);

        ObjectNode saveAsItem = json.objectNode();
        saveAsItem.put(BUTTON_NAME, "saveAsItem");
        saveAsItem.put(SHORTCUT_KEY_VALUE, "ctrl+alt+s");
        shortcutKeyArray.add(saveAsItem);

        ObjectNode lineFeedItem = json.objectNode();
        lineFeedItem.put(BUTTON_NAME, "lineFeedItem");
        lineFeedItem.put(SHORTCUT_KEY_VALUE, "");
        shortcutKeyArray.add(lineFeedItem);

        ObjectNode openConfigItem = json.objectNode();
        openConfigItem.put(BUTTON_NAME, "openConfigItem");
        openConfigItem.put(SHORTCUT_KEY_VALUE, "alt+s");
        shortcutKeyArray.add(openConfigItem);

        ObjectNode addItem = json.objectNode();
        addItem.put(BUTTON_NAME, "addItem");
        addItem.put(SHORTCUT_KEY_VALUE, "");
        shortcutKeyArray.add(addItem);

        ObjectNode countItem = json.objectNode();
        countItem.put(BUTTON_NAME, "countItem");
        countItem.put(SHORTCUT_KEY_VALUE, "");
        shortcutKeyArray.add(countItem);

        return json;
    }
}
