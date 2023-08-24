package org.jcnc.jnotepad.init;

import org.jcnc.jnotepad.tool.LogUtil;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
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

    private String appConfigDir;
    Logger logger = LogUtil.getLogger(this.getClass());

    public Config() {
        appConfigDir = System.getProperty("user.home") + File.separator + ".jnotepad";
        boolean isConfigDirReady = Paths.get(appConfigDir).toFile().mkdirs();
        if (!isConfigDirReady) {
            appConfigDir = "/tmp";
        }
    }

    /**
     * 从文件中读取属性配置。
     *
     * @return 包含从文件中读取的属性的 Properties 对象。
     */
    public Properties readPropertiesFromFile() {
        Properties properties = new Properties();

        //设置语言包
        languagePackName = EN_LANGUAGE_PACK_NAME;
        try (InputStream inputStream = new FileInputStream(languagePackName)) {
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
        Properties chLanguagePack = getChineseLanguagePack();

        Properties enLanguagePack = getEnglishLanguagePack();

        String chineseFilePath = Paths.get(appConfigDir, CH_LANGUAGE_PACK_NAME).toString();
        try (OutputStream outputStream = new FileOutputStream(chineseFilePath)) {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);  // 使用 UTF-8 编码
            chLanguagePack.store(writer, JNOTEPAD_CH_LANGUAGE_PACK_NAME);

        } catch (IOException ignored) {
            logger.info("未检测到中文语言包!");
        }

        String enFilePath = Paths.get(appConfigDir, EN_LANGUAGE_PACK_NAME).toString();
        try (OutputStream outputStream = new FileOutputStream(enFilePath)) {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);  // 使用 UTF-8 编码
            enLanguagePack.store(writer, JNOTEPAD_EN_LANGUAGE_PACK_NAME);

        } catch (IOException ignored) {
            logger.info("未检测到英文语言包!");
        }
    }

    private static Properties getChineseLanguagePack() {
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
        properties.setProperty("OPEN_CONFIGURATION_FILE", "打开配置文件");
        properties.setProperty("PLUGIN", "插件");
        properties.setProperty("ADD_PLUGIN", "增加插件");
        properties.setProperty("STATISTICS", "统计字数");
        properties.setProperty("ROW", "行数");
        properties.setProperty("COLUMN", "列数");
        properties.setProperty("WORD_COUNT", "字数");
        properties.setProperty("ENCODE", "编码");
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
        return properties;
    }

    public String getAppConfigDir() {
        return appConfigDir;
    }
}
