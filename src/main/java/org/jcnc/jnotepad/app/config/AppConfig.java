package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.jcnc.jnotepad.app.common.constants.AppConstants.DEFAULT_PROPERTY;
import static org.jcnc.jnotepad.app.common.constants.AppConstants.PROGRAM_FILE_DIRECTORY;

/**
 * 应用程序配置文件
 *
 * <p>
 * 此类用于存储应用程序的配置信息，包括程序根路径、排除的文件夹和文件等。
 * </p>
 *
 * @author gewuyou
 */
public class AppConfig {
    /**
     * 排除的文件夹
     */
    @JsonIgnore
    private final Set<File> ignoreFolder;
    /**
     * 排除的文件
     */
    @JsonIgnore
    private final Set<File> ignoreFile;
    /**
     * 程序根路径
     */
    private String rootPath;
    /**
     * 上次的程序根路径
     */
    @JsonIgnore
    private String lastRootPath;

    /**
     * 构造应用程序配置对象
     */
    public AppConfig() {
        ignoreFolder = Set.of(
                new File(Paths.get(System.getProperty(DEFAULT_PROPERTY), PROGRAM_FILE_DIRECTORY, "system").toString()),
                new File(Paths.get(System.getProperty(DEFAULT_PROPERTY), PROGRAM_FILE_DIRECTORY, "logs").toString())
        );
        ignoreFile = Collections.emptySet();
    }

    /**
     * 获取程序根路径
     *
     * @return 程序根路径
     */
    public String getRootPath() {
        return Optional.ofNullable(rootPath).orElse(System.getProperty(DEFAULT_PROPERTY));
    }

    /**
     * 设置程序根路径
     *
     * @param rootPath 程序根路径
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 获取上次的程序根路径
     *
     * @return 上次的程序根路径
     */
    public String getLastRootPath() {
        return lastRootPath;
    }

    /**
     * 设置上次的程序根路径
     *
     * @param lastRootPath 上次的程序根路径
     */
    public void setLastRootPath(String lastRootPath) {
        this.lastRootPath = lastRootPath;
    }

    /**
     * 获取排除的文件夹集合
     *
     * @return 排除的文件夹集合
     */
    public Set<File> getIgnoreFolder() {
        return ignoreFolder;
    }

    /**
     * 获取排除的文件集合
     *
     * @return 排除的文件集合
     */
    public Set<File> getIgnoreFile() {
        return ignoreFile;
    }
}
