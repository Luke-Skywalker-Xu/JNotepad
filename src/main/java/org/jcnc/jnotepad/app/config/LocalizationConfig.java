package org.jcnc.jnotepad.app.config;

import org.jcnc.jnotepad.tool.LogUtil;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.jcnc.jnotepad.constants.AppConstants.CH_LANGUAGE_PACK_NAME;
import static org.jcnc.jnotepad.constants.AppConstants.EN_LANGUAGE_PACK_NAME;
import static org.jcnc.jnotepad.constants.TextConstants.TEXT_WRAP;

/**
 * 本地化配置文件
 *
 * @author gewuyou
 */
public class LocalizationConfig {
    Logger logger = LogUtil.getLogger(this.getClass());
    private static final LocalizationConfig LOCALIZATION_CONFIG = new LocalizationConfig();
    private final Properties properties = new Properties();

    /**
     * 本地化语言包名，默认英文
     */
    private String languagePackName = EN_LANGUAGE_PACK_NAME;

    private LocalizationConfig() {

    }

    /**
     * 初始化本地化语言包
     *
     * @since 2023/8/25 18:18
     */
    public void initLocalizationConfig() {
        // 设置语言包
        try (InputStream inputStream = new FileInputStream(languagePackName)) {
            // 使用 UTF-8 编码
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(reader);
        } catch (IOException e) {
            // 如果读取出错，则调用初始化方法
            initializePropertiesFile();
        }
        logger.info("初始化本地化语言包成功!");
        title = properties.getProperty("title", "JNotepad");
        sava = properties.getProperty("SAVA");
        file = properties.getProperty("FILE");
        newly = properties.getProperty("NEW");
        open = properties.getProperty("OPEN");
        savaAs = properties.getProperty("SAVA_AS");
        set = properties.getProperty("SET");
        wordWrap = properties.getProperty("WORD_WRAP");
        plugin = properties.getProperty("PLUGIN");
        addPlugin = properties.getProperty("ADD_PLUGIN");
        statistics = properties.getProperty("STATISTICS");
        openConfigurationFile = properties.getProperty("OPEN_CONFIGURATION_FILE");
        top = properties.getProperty("TOP");
        language = properties.getProperty("LANGUAGE");
        chinese = properties.getProperty("CHINESE");
        english = properties.getProperty("ENGLISH");
        textWrap = properties.getProperty(TEXT_WRAP, "true");
        newFile = properties.getProperty("NEW_FILE");
        unknown = properties.getProperty("UNKNOWN");
        row = properties.getProperty("ROW");
        column = properties.getProperty("COLUMN");
        wordCount = properties.getProperty("WORD_COUNT");
        encode = properties.getProperty("ENCODE");
    }

    /**
     * 初始化属性配置文件。
     * 如果属性文件不存在，将创建一个新的属性文件并设置默认属性。
     */
    public void initializePropertiesFile() {
        switch (languagePackName) {
            case CH_LANGUAGE_PACK_NAME -> {
                setChineseLanguagePack();
                createLanguagePacks(languagePackName, properties);
            }
            case EN_LANGUAGE_PACK_NAME -> {
                setEnglishLanguagePack();
                createLanguagePacks(languagePackName, properties);
            }
            default -> logger.error("语言包加载错误!");
        }

    }

    private void createLanguagePacks(String languagePackName, Properties languagePack) {
        try (OutputStream outputStream = new FileOutputStream(languagePackName)) {
            // 使用 UTF-8 编码
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            languagePack.store(writer, languagePackName);
        } catch (IOException ignored) {
            logger.info("未检测到语言包!");
        }
    }

    private void setChineseLanguagePack() {
        properties.clear();
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
    }

    private void setEnglishLanguagePack() {
        properties.clear();
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
    }

    private String textWrap;
    /// 应用程序文本

    private String title;

    ///菜单栏文本

    private String sava;

    private String file;

    private String newly;

    private String open;

    private String savaAs;

    private String set;

    private String wordWrap;

    private String plugin;

    private String addPlugin;

    private String statistics;

    private String openConfigurationFile;

    private String top;

    private String language;

    private String chinese;

    private String english;

    /// NewFile 文本常量

    private String newFile;
    /// EncodingDetector 文本常量
    private String unknown;

    /// JNotepadStatusBox
    private String row;

    private String column;

    private String wordCount;

    private String encode;

    public static LocalizationConfig getLocalizationConfig() {
        return LOCALIZATION_CONFIG;
    }

    public void setLanguagePackName(String languagePackName) {
        this.languagePackName = languagePackName;
    }

    public String getTitle() {
        return title;
    }

    public String getSava() {
        return sava;
    }

    public String getFile() {
        return file;
    }

    public String getNewly() {
        return newly;
    }

    public String getOpen() {
        return open;
    }

    public String getSavaAs() {
        return savaAs;
    }

    public String getSet() {
        return set;
    }

    public String getWordWrap() {
        return wordWrap;
    }

    public String getPlugin() {
        return plugin;
    }

    public String getAddPlugin() {
        return addPlugin;
    }

    public String getStatistics() {
        return statistics;
    }

    public String getOpenConfigurationFile() {
        return openConfigurationFile;
    }

    public String getTop() {
        return top;
    }

    public String getLanguage() {
        return language;
    }

    public String getChinese() {
        return chinese;
    }

    public String getEnglish() {
        return english;
    }

    public String getNewFile() {
        return newFile;
    }

    public String getUnknown() {
        return unknown;
    }

    public String getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public String getWordCount() {
        return wordCount;
    }

    public String getEncode() {
        return encode;
    }

    public String getLanguagePackName() {
        return languagePackName;
    }

    public String getTextWrap() {
        return textWrap;
    }

    public void setTextWrap(String textWrap) {
        this.textWrap = textWrap;
        properties.setProperty(TEXT_WRAP, textWrap);
    }
}
