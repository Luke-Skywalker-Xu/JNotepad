package org.jcnc.jnotepad.tool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jcnc.jnotepad.exception.AppException;

/**
 * jackson解析器的facade类，主要提供objectMapper对象
 *
 * @author songdragon
 */
public class JsonUtil {
    private JsonUtil() {
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static String toJsonString(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new AppException(e);
        }
    }
}
