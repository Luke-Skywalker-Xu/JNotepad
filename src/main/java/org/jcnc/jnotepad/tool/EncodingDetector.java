package org.jcnc.jnotepad.tool;


import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import org.jcnc.jnotepad.app.config.LocalizationConfig;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;


/**
 * 编码检测工具类
 *
 * @author 许轲
 */
public class EncodingDetector {
    static LocalizationConfig localizationConfig = LocalizationConfig.getLocalizationConfig();
    private static final Logger LOG = LogUtil.getLogger(EncodingDetector.class);
    /**
     * 编码侦测概率，阈值：50%
     */
    public static final int THRESHOLD_CONFIDENCE = 50;


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
            CharsetMatch[] matchList = charsetDetector.detectAll();
            if (matchList == null || matchList.length == 0) {
                return localizationConfig.getUnknown();
            }
            CharsetMatch maxConfidence = matchList[0];
            if (maxConfidence.getConfidence() < THRESHOLD_CONFIDENCE) {
                return localizationConfig.getUnknown();
            }
            for (int i = 1; i < matchList.length; i++) {
                CharsetMatch match = matchList[i];
                LOG.debug("{} : {}", match.getName(), match.getConfidence());
                if (match.getConfidence() >= THRESHOLD_CONFIDENCE && match.getConfidence() >= maxConfidence.getConfidence()) {
                    maxConfidence = match;
                } else {
                    return maxConfidence.getName();
                }
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return localizationConfig.getUnknown();
    }

    /**
     * 检测文本编码
     *
     * @param file 文件
     * @return Charset编码
     */
    public static Charset detectEncodingCharset(File file) {
        String charset = detectEncoding(file);
        if (charset.equals(localizationConfig.getUnknown())) {
            return Charset.defaultCharset();
        }
        return Charset.forName(charset);
    }
}