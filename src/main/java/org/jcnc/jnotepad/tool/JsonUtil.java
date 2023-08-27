package org.jcnc.jnotepad.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jcnc.jnotepad.exception.AppException;

import static com.fasterxml.jackson.core.util.DefaultIndenter.SYS_LF;

/**
 * jackson解析器的facade类，主要提供objectMapper对象
 *
 * @author songdragon
 */
public class JsonUtil {
    private JsonUtil() {
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        DefaultIndenter di = new DefaultIndenter("    ", SYS_LF);
        prettyPrinter.indentArraysWith(di);
        prettyPrinter.indentObjectsWith(di);
        OBJECT_MAPPER.setDefaultPrettyPrinter(prettyPrinter);
    }

    public static String toJsonString(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new AppException(e);
        }
    }
}
