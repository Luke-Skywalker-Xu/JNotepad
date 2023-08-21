package org.jcnc.jnotepad.exception;
import org.jcnc.jnotepad.tool.LogUtil;

/**
 * 应用异常类
 *
 * @author gewuyou
 */
public class AppException extends RuntimeException{
    public AppException(String message) {
        super(message);
        LogUtil.error(message,this.getClass());
    }
}
