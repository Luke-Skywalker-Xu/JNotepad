package org.jcnc.jnotepad.app.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.jcnc.jnotepad.common.constants.AppConstants.DEFAULT_PROPERTY;
import static org.jcnc.jnotepad.common.constants.AppConstants.PROGRAM_FILE_DIRECTORY;

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
     * 上次的程序根路径
     */
    @JsonIgnore
    private String lastRootPath;

    public AppConfig() {
        ignoreFolder = Set.of(
                new File(Paths.get(System.getProperty(DEFAULT_PROPERTY), PROGRAM_FILE_DIRECTORY, "system").toString()),
                new File(Paths.get(System.getProperty(DEFAULT_PROPERTY), PROGRAM_FILE_DIRECTORY, "logs").toString())
        );
        ignoreFile = Collections.emptySet();
    }

    public String getRootPath() {
        return Optional.ofNullable(rootPath).orElse(System.getProperty(DEFAULT_PROPERTY));
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getLastRootPath() {
        return lastRootPath;
    }

    public void setLastRootPath(String lastRootPath) {
        this.lastRootPath = lastRootPath;
    }

    public Set<File> getIgnoreFolder() {
        return ignoreFolder;
    }

    public Set<File> getIgnoreFile() {
        return ignoreFile;
    }
}
