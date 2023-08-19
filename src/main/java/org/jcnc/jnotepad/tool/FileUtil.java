package org.jcnc.jnotepad.tool;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 一个大转盘
 * @date 2023/8/18
 */
public class FileUtil {

    /**
     * 把一个文件中的内容读取成一个String字符串
     * @date 2023/8/18 0:54
     * @param jsonFile
     * @return String
     */
    public static String getJsonStr(File jsonFile){
        String jsonStr = "";
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
