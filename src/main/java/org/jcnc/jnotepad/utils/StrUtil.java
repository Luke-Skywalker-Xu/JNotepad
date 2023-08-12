package org.jcnc.jnotepad.utils;

/**
 * @Description
 * @Author lixinpiao
 * @Date 2023/8/12 15:05
 **/
public class StrUtil {

    public static int countCharacters(String str, char target) {
        int count = 0;
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (aChar == target) {
                count++;
            }
        }
        return count;
    }
}
