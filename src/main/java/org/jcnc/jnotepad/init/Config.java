package org.jcnc.jnotepad.init;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.jcnc.jnotepad.constants.AppConstants.CH_LANGUAGE_PACK_NAME;
import static org.jcnc.jnotepad.constants.AppConstants.EN_LANGUAGE_PACK_NAME;

/**
 * @author 许轲
 * 该类负责配置文件的读取和初始化操作。
 */
public class Config {

    String LANGUAGE_PACK_NAME;

    /**
     * 从文件中读取属性配置。
     *
     * @return 包含从文件中读取的属性的 Properties 对象。
     */
    public Properties readPropertiesFromFile() {
        Properties properties = new Properties();

        LANGUAGE_PACK_NAME = EN_LANGUAGE_PACK_NAME;
        try (InputStream inputStream = new FileInputStream(LANGUAGE_PACK_NAME)) {
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);  // 使用 UTF-8 编码
            properties.load(reader);
        } catch (IOException e) {
            // 如果读取出错，则调用初始化方法
            initializePropertiesFile();
        }
        return properties;
    }

    /**
     * 初始化属性配置文件。
     * 如果属性文件不存在，将创建一个新的属性文件并设置默认属性。
     */
    public void initializePropertiesFile() {
        Properties chLanguagePack = getCHLanguagePack();

        Properties enLanguagePack = getENLanguagePack();

        try (OutputStream outputStream = new FileOutputStream(CH_LANGUAGE_PACK_NAME)) {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);  // 使用 UTF-8 编码
            chLanguagePack.store(writer, "JNotepad ch_language_pack");

        } catch (IOException ignored) {
        }

        try (OutputStream outputStream = new FileOutputStream(EN_LANGUAGE_PACK_NAME)) {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);  // 使用 UTF-8 编码
            enLanguagePack.store(writer, "JNotepad en_language_pack");

        } catch (IOException ignored) {
        }
    }

    private static Properties getCHLanguagePack() {
        Properties properties = new Properties();

        properties.setProperty("TITLE", "JNotepad");  // 设置默认属性
        properties.setProperty("NEW_FILE", "新建文件");
        properties.setProperty("SAVA", "保存");
        properties.setProperty("FILE", "文件");
        properties.setProperty("NEW", "新建");
        properties.setProperty("OPEN", "打开");
        properties.setProperty("SAVA_AS", "另存为");
        properties.setProperty("SET", "设置");
        properties.setProperty("WORD_WRAP", "自动换行");
        properties.setProperty("PLUGIN", "插件");
        properties.setProperty("ADD_PLUGIN", "增加插件");
        properties.setProperty("STATISTICS", "统计字数");
        properties.setProperty("ROW", "行数");
        properties.setProperty("COLUMN", "列数");
        properties.setProperty("WORD_COUNT", "字数");
        properties.setProperty("ENCODE", "编码");
        return properties;
    }

    private static Properties getENLanguagePack() {
        Properties properties = new Properties();

        properties.setProperty("TITLE", "JNotepad");
        properties.setProperty("NEW_FILE", "New File");
        properties.setProperty("SAVA", "Save");
        properties.setProperty("FILE", "File");
        properties.setProperty("NEW", "New");
        properties.setProperty("OPEN", "Open");
        properties.setProperty("SAVA_AS", "Save As");
        properties.setProperty("SET", "Settings");
        properties.setProperty("WORD_WRAP", "Word Wrap");
        properties.setProperty("PLUGIN", "Plugins");
        properties.setProperty("ADD_PLUGIN", "Add Plugin");
        properties.setProperty("STATISTICS", "Word Count");
        properties.setProperty("ROW", "Row");
        properties.setProperty("COLUMN", "Column");
        properties.setProperty("WORD_COUNT", "Word Count");
        properties.setProperty("ENCODE", "Encoding");
        return properties;
    }

}
