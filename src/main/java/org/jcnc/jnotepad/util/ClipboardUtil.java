package org.jcnc.jnotepad.util;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * 剪切板工具
 *
 * @author gewuyou
 */
public class ClipboardUtil {
    /**
     * 系统剪切板对象
     */
    private static final Clipboard CLIPBOARD = Clipboard.getSystemClipboard();

    private ClipboardUtil() {
    }

    /**
     * Writes the provided text to the system clipboard.
     *
     * @param text the text to be written to the clipboard
     */
    public static void writeTextToClipboard(String text) {
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        CLIPBOARD.setContent(content);
    }

    /**
     * Reads text from the clipboard.
     *
     * @return the text read from the clipboard
     */
    public static String readTextFromClipboard() {
        String text = CLIPBOARD.getString();
        LogUtil.getLogger(ClipboardUtil.class).info("剪切板内容:{}", text);
        return text;
    }

}
