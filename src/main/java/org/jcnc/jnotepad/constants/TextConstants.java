package org.jcnc.jnotepad.constants;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jcnc.jnotepad.exception.AppException;
import org.jcnc.jnotepad.json.DataGenerator;
import org.jcnc.jnotepad.json.MyData;
import org.jcnc.jnotepad.tool.JsonUtil;

/**
 * 文本常量，被多处使用的常量放到此处。如果只有一个class使用，在class中使用private static final声明。
 *
 * @author gewuyou
 */
public class TextConstants {

    private TextConstants() {
    }


    /// GlobalConfig文本常量

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
    public static final String ROW = "ROW";
    public static final String COLUMN = "COLUMN";
    public static final String WORD_COUNT = "WORD_COUNT";
    public static final String ENCODE = "ENCODE";

    /// Config 文本常量
    public static final String ENGLISH = "english";

    public static final String CHINESE = "chinese";

    public static final String LOWER_LANGUAGE = "language";

    /// 配置文件文本常量
    /**
     * 内置配置文件
     */
    public static final String JNOTEPAD_CONFIG;

    static {
        try {
            JNOTEPAD_CONFIG = createShortcutKeyJsonString();
        } catch (JsonProcessingException e) {
            throw new AppException(e);
        }
    }

    private static String createShortcutKeyJsonString() throws JsonProcessingException {
        return JsonUtil.toJsonString(createShortcutKeyJson());
    }

    public static ObjectNode createShortcutKeyJson() throws JsonProcessingException {
        MyData myData = DataGenerator.generateMyData();

        // 创建 ObjectMapper 和 ObjectWriter 来将对象转换为 JSON
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

        // 将 MyData 对象转换为 JSON 字符串
        String json = writer.writeValueAsString(myData);

        // 将 JSON 字符串转换为 ObjectNode 对象

        return objectMapper.readValue(json, ObjectNode.class);
    }
}
