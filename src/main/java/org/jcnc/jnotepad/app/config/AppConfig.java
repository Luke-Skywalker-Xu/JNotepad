package org.jcnc.jnotepad.app.config;

import java.util.Optional;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;

/**
 * 应用程序配置文件
 *
 * @author gewuyou
 */
public class AppConfig {
    /**
     * 程序根路径
     */
    private String rootPath;

    public String getRootPath() {
        return Optional.of(rootPath).orElse(System.getProperty(DEFAULT_PROPERTY));
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
