package org.jcnc.jnotepad.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具
 *
 * <p>注意：如果在JavaFX项目中需要调用日志，请使用Platform.runLater()来调用。</p>
 *
 * @author gewuyou
 */
public class LoggerUtil {
    private LoggerUtil() {
    }

    /**
     * 获取日志类。
     *
     * @param currentClass 所要记录的类
     * @return org.slf4j.Logger 日志对象
     *
     * <p>传入当前需要记录的类，返回记录该类的日志类。</p>
     * <p>建议一个类调用超过两次这个方法时，应当将该日志类变成成员对象，而不是多次调用。</p>
     */
    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }
}
