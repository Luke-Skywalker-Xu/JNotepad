package org.jcnc.jnotepad.exception;

/**
 * 应用异常类，用于处理应用程序中的异常情况。
 *
 * <p>应用异常是一个运行时异常，通常用于捕获和处理应用程序中的不可预料的错误和异常情况。</p>
 *
 * @author gewuyou
 */
public class AppException extends RuntimeException {

    /**
     * 构造一个应用异常对象。
     *
     * @param message 异常消息
     */
    public AppException(String message) {
        super(message);
    }

    /**
     * 构造一个应用异常对象。
     *
     * @param cause 异常的原因
     */
    public AppException(Throwable cause) {
        super(cause);
    }
}
