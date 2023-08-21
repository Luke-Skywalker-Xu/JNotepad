package org.jcnc.jnotepad.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author gewuyou
 */
public class LogUtil {
    private LogUtil() {
    }

    /**
     * 日志信息打印
     *
     * @param message 日志信息
     * @param currentClass 所要记录的类
     *
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */
    public static void info(String message, Class<?> currentClass) {
        Logger logger = LoggerFactory.getLogger(currentClass);
        logger.info(message);
    }
    /**
     * 日志排错信息打印
     *
     * @param message 日志信息
     * @param currentClass 所要记录的类
     *
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */

    public static void debug(String message, Class<?> currentClass) {
        Logger logger = LoggerFactory.getLogger(currentClass);
        logger.debug(message);
    }
    /**
     * 日志警告信息打印
     *
     * @param message 日志信息
     * @param currentClass 所要记录的类
     *
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */
    public static void warn(String message, Class<?> currentClass) {
        Logger logger = LoggerFactory.getLogger(currentClass);
        logger.warn(message);
    }
    /**
     * 日志错误信息打印
     *
     * @param message 日志信息
     * @param currentClass 所要记录的类
     *
     * @apiNote 传入当前需要记录的类，方便知晓是哪个位置记录的日志
     */
    public static void error(String message, Class<?> currentClass) {
        Logger logger = LoggerFactory.getLogger(currentClass);
        logger.error(message);
    }
    /**
     * 日志错误信息打印
     *
     * @param message 日志信息
     * @param throwable 抛出的异常
     * @param currentClass 所要记录的类
     *
     * @apiNote 这个方法用来弥补当抛出异常或手动抛出异常时，无法捕捉到所抛异常
     */

    public static void error(String message, Throwable throwable, Class<?> currentClass) {
        Logger logger = LoggerFactory.getLogger(currentClass);
        logger.error(message, throwable);
    }
}

