package org.jcnc.jnotepad.tool;


import org.jcnc.jnotepad.ui.LineNumberTextArea;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 编码检测工具类
 */
public class EncodingDetector {

    /**
     * 检测 TextArea 中的文本编码
     *
     * @param textArea 文本区域
     * @return 字符串表示的编码，如果检测失败则返回 "UNKNOWN"
     */
    public static String detectEncoding(LineNumberTextArea textArea) {
        String text = textArea.getMainTextArea().getText();

        return detectEncoding(text);
    }

    /**
     * 检测文本编码
     *
     * @param text 要检测的文本
     * @return 字符串表示的编码，如果检测失败则返回 "UNKNOWN"
     */
    public static String detectEncoding(String text) {
        // 尝试常见的编码
        for (Charset charset : commonCharsets()) {
            if (isValidEncoding(text, charset)) {
                System.out.println("编码监测结果:" + isValidEncoding(text, charset));
                return charset.name();
            }
        }

        return "UNKNOWN";
    }

    /**
     * 获取常见的字符集数组
     *
     * @return 常见字符集数组
     */
    private static Charset[] commonCharsets() {
        return new Charset[]{
                StandardCharsets.UTF_8,
                StandardCharsets.UTF_16,
                StandardCharsets.UTF_16LE,
                StandardCharsets.UTF_16BE,
                StandardCharsets.ISO_8859_1
        };
    }

    /**
     * 检查指定编码是否能够正确解码文本
     *
     * @param text     要检测的文本
     * @param encoding 要尝试的编码
     * @return 如果指定编码能够正确解码文本，则返回 true，否则返回 false
     */
    private static boolean isValidEncoding(String text, Charset encoding) {
        // 尝试使用指定编码解码
        byte[] bytes = text.getBytes(encoding);
        String decoded = new String(bytes, encoding);

        // 解码后的文本相同表示编码有效
        return text.equals(decoded);
    }

}