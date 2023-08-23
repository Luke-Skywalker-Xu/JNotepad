package org.jcnc.jnotepad.tool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具<br>注意，使用该工具的方法时，如果需要在JavaFx项目中调用日志请使用Platform.runLater()调用
 *
 * @author gewuyou
 */
public class LogUtil {
    private LogUtil() {
    }

    /**
     * 获取日志类
     *
     * @param currentClass 所要记录的类
     * @return org.apache.logging.log4j.Logger 日志对象
     * @apiNote 传入当前需要记录的类，返回记录该类的日志类，也可直接使用工具类定义好的方法，也可调用这个方法自定义
     * @see LogUtil
     */
    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }

    /**
     * 日志信息打印
     *
     * @param message      日志信息
     * @param currentClass 所要记录的类
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */
    public static void info(String message, Class<?> currentClass) {
        getLogger(currentClass).info(message);
    }

    /**
     * 日志排错信息打印
     *
     * @param message      日志信息
     * @param currentClass 所要记录的类
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */

    public static void debug(String message, Class<?> currentClass) {
        getLogger(currentClass).debug(message);
    }

    /**
     * 日志警告信息打印
     *
     * @param message      日志信息
     * @param currentClass 所要记录的类
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */
    public static void warn(String message, Class<?> currentClass) {
        getLogger(currentClass).warn(message);
    }

    /**
     * 日志错误信息打印
     *
     * @param message      日志信息
     * @param currentClass 所要记录的类
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */
    public static void error(String message, Class<?> currentClass) {
        getLogger(currentClass).error(message);
    }

    /**
     * 日志错误信息打印
     *
     * @param message      日志信息
     * @param throwable    抛出的异常
     * @param currentClass 所要记录的类
     * @apiNote 这个方法用来弥补当抛出异常或手动抛出异常时，无法捕捉到所抛异常
     */

    public static void error(String message, Throwable throwable, Class<?> currentClass) {
        getLogger(currentClass).error(message, throwable);
    }
}
