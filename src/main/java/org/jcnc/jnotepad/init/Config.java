package org.jcnc.jnotepad.init;

import org.jcnc.jnotepad.tool.LogUtil;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.jcnc.jnotepad.constants.AppConstants.CH_LANGUAGE_PACK_NAME;
import static org.jcnc.jnotepad.constants.AppConstants.EN_LANGUAGE_PACK_NAME;
import static org.jcnc.jnotepad.constants.TextConstants.JNOTEPAD_CH_LANGUAGE_PACK_NAME;
import static org.jcnc.jnotepad.constants.TextConstants.JNOTEPAD_EN_LANGUAGE_PACK_NAME;

/**
 * @author 许轲
 * 该类负责配置文件的读取和初始化操作。
 */
public class Config {

    private String languagePackName;

    Logger logger = LogUtil.getLogger(this.getClass());

    public void setLanguagePackName(String languagePackName) {
        this.languagePackName = languagePackName;
    }

    public String getLanguagePackName() {
        return languagePackName;
    }

    /**
     * 从文件中读取属性配置。
     *
     * @return 包含从文件中读取的属性的 Properties 对象。
     */
    public Properties readPropertiesFromFile() {
        Properties properties = new Properties();
        // 设置语言包
        languagePackName = EN_LANGUAGE_PACK_NAME;
        try (InputStream inputStream = new FileInputStream(languagePackName)) {
            // 使用 UTF-8 编码
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
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
        Properties chLanguagePack = getChineseLanguagePack();

        Properties enLanguagePack = getEnglishLanguagePack();

        try (OutputStream outputStream = new FileOutputStream(CH_LANGUAGE_PACK_NAME)) {
            // 使用 UTF-8 编码
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            chLanguagePack.store(writer, JNOTEPAD_CH_LANGUAGE_PACK_NAME);

        } catch (IOException ignored) {
            logger.info("未检测到中文语言包!");
        }

        try (OutputStream outputStream = new FileOutputStream(EN_LANGUAGE_PACK_NAME)) {
            // 使用 UTF-8 编码
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            enLanguagePack.store(writer, JNOTEPAD_EN_LANGUAGE_PACK_NAME);

        } catch (IOException ignored) {
            logger.info("未检测到英文语言包!");
        }
    }

    private static Properties getChineseLanguagePack() {
        Properties properties = new Properties();
        // 设置默认属性
        properties.setProperty("TITLE", "JNotepad");
        properties.setProperty("NEW_FILE", "新建文件");
        properties.setProperty("SAVA", "保存");
        properties.setProperty("FILE", "文件");
        properties.setProperty("NEW", "新建");
        properties.setProperty("OPEN", "打开");
        properties.setProperty("SAVA_AS", "另存为");
        properties.setProperty("SET", "设置");
        properties.setProperty("WORD_WRAP", "自动换行");
        properties.setProperty("OPEN_CONFIGURATION_FILE", "打开配置文件");
        properties.setProperty("PLUGIN", "插件");
        properties.setProperty("ADD_PLUGIN", "增加插件");
        properties.setProperty("STATISTICS", "统计字数");
        properties.setProperty("ROW", "行数");
        properties.setProperty("COLUMN", "列数");
        properties.setProperty("WORD_COUNT", "字数");
        properties.setProperty("ENCODE", "编码");
        properties.setProperty("TOP", "窗口置顶");
        properties.setProperty("LANGUAGE", "语言");
        properties.setProperty("CHINESE", "中文");
        properties.setProperty("ENGLISH", "英文");
        return properties;
    }

    private static Properties getEnglishLanguagePack() {
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
        properties.setProperty("OPEN_CONFIGURATION_FILE", "Open Configuration File");
        properties.setProperty("PLUGIN", "Plugins");
        properties.setProperty("ADD_PLUGIN", "Add Plugin");
        properties.setProperty("STATISTICS", "Word Count");
        properties.setProperty("ROW", "Row");
        properties.setProperty("COLUMN", "Column");
        properties.setProperty("WORD_COUNT", "Word Count");
        properties.setProperty("ENCODE", "Encoding");
        properties.setProperty("TOP", "Window Top");
        properties.setProperty("LANGUAGE", "Language");
        properties.setProperty("CHINESE", "Chinese");
        properties.setProperty("ENGLISH", "English");
        return properties;
    }

}
