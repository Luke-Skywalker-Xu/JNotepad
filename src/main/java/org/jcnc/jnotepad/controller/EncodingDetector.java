package org.jcnc.jnotepad.controller;

import javafx.scene.control.TextArea;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EncodingDetector {

  public static String detectEncoding(TextArea textArea) {
    String text = textArea.getText();
    
    return detectEncoding(text); 
  }

  public static String detectEncoding(String text) {
    // 尝试常见的编码
    for (Charset charset : commonCharsets()) {
      if (isValidEncoding(text, charset)) {
        System.out.println(isValidEncoding(text, charset));
        return charset.name();
      }
    }
    
    return "UNKNOWN";
  }

  private static Charset[] commonCharsets() {
    return new Charset[] { 
      StandardCharsets.UTF_8,
      StandardCharsets.UTF_16, 
      StandardCharsets.UTF_16LE,
      StandardCharsets.UTF_16BE,
      StandardCharsets.ISO_8859_1
    };
  }

  private static boolean isValidEncoding(String text, Charset encoding) {
    // 尝试使用指定编码解码
    byte[] bytes = text.getBytes(encoding);
    String decoded = new String(bytes, encoding);


    // 解码后的文本相同表示编码有效
    return text.equals(decoded);
  }

}