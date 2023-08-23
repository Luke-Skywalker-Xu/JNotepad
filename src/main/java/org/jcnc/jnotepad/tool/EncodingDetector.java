package org.jcnc.jnotepad.tool;


import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import javafx.application.Platform;
import org.jcnc.jnotepad.ui.LineNumberTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 编码检测工具类
 *
 * @author 许轲
 */
public class EncodingDetector {

    private static final Logger LOG = LoggerFactory.getLogger(EncodingDetector.class);
    public static final String UNKNOWN = "UNKNOWN";

    private EncodingDetector() {
    }

    /**
     * 检测文本编码
     *
     * @param file 要检测的文件
     * @return 字符串表示的编码，如果检测失败则返回 "UNKNOWN"
     */
    public static String detectEncoding(File file) {
        CharsetDetector charsetDetector = new CharsetDetector();
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file.getPath()))) {
            charsetDetector.setText(inputStream);
            CharsetMatch match = charsetDetector.detect();
            LOG.debug(match.getName() + " " + match.getConfidence());
            if (match.getConfidence() > 50) {
                return match.getName();
            }
        } catch (Exception e) {
            LOG.error("", e);
        }

        return UNKNOWN;
    }

    /**
     * 检测文本编码
     *
     * @param file 文件
     * @return Charset编码
     */
    public static Charset detectEncodingCharset(File file) {
        String charset = detectEncoding(file);
        if (charset.equals(UNKNOWN)) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(charset);
    }
}