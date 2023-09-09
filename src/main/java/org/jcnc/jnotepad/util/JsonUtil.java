package org.jcnc.jnotepad.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jcnc.jnotepad.exception.AppException;

import static com.fasterxml.jackson.core.util.DefaultIndenter.SYS_LF;

/**
 * Jackson 解析器的外观类，主要提供 ObjectMapper 对象。
 *
 * <p>该类用于封装 Jackson 对象映射工具的配置和操作。</p>
 *
 * @author songdragon
 */
public class JsonUtil {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        DefaultIndenter di = new DefaultIndenter("    ", SYS_LF);
        prettyPrinter.indentArraysWith(di);
        prettyPrinter.indentObjectsWith(di);
        OBJECT_MAPPER.setDefaultPrettyPrinter(prettyPrinter);
    }

    private JsonUtil() {
    }

    /**
     * 将对象转换为 JSON 字符串。
     *
     * @param o 要转换的对象
     * @return 对象的 JSON 表示，如果转换失败则抛出 AppException 异常
     * @throws AppException 如果转换失败
     */
    public static String toJsonString(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new AppException(e);
        }
    }
}
