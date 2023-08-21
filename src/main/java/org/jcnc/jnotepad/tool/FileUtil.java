package org.jcnc.jnotepad.tool;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 一个大转盘
 */
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 把一个文件中的内容读取成一个String字符串
     *
     * @param jsonFile json文件
     * @return String
     */
    public static String getJsonStr(File jsonFile) {
        String jsonStr;
        try (
                Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)
        ) {

            int ch;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
