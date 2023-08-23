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
     * @apiNote 传入当前需要记录的类，返回记录该类的日志类 <br>建议一个类调用超过两次这个方法时，应当将该日志类变成成员对象，而不是多次调用
     */
    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }
}
