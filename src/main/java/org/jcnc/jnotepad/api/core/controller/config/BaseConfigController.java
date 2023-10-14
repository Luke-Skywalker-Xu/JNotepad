package org.jcnc.jnotepad.api.core.controller.config;

import org.jcnc.jnotepad.api.core.controller.interfaces.ConfigController;
import org.jcnc.jnotepad.app.utils.JsonUtil;
import org.jcnc.jnotepad.app.utils.LogUtil;
import org.jcnc.jnotepad.app.utils.PopUpUtil;
import org.jcnc.jnotepad.controller.exception.AppException;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 抽象基本配置文件控制器类。
 * <p>
 * 该类是基本配置文件控制器的抽象实现，提供了加载、持久化配置文件以及其他相关方法。
 * </p>
 *
 * @param <T> 配置文件类型
 * @author gewuyou
 */
public abstract class BaseConfigController<T> implements ConfigController<T> {

    protected static final String ROOT_CONFIG_DIR = "config";

    protected static final String SYSTEM_CONFIG_DIR = "system";
    private final Logger logger = LogUtil.getLogger(getClass());
    protected T config;

    /**
     * 获取配置文件Class类。
     *
     * @return 配置文件Class类
     */
    protected abstract Class<T> getConfigClass();

    /**
     * 获取配置文件名称。
     *
     * @return 配置文件名称
     */
    protected abstract String getConfigName();

    /**
     * 获取配置文件文件夹路径。
     *
     * @return 配置文件夹路径
     */
    protected abstract String getConfigDir();

    /**
     * 获取配置文件对象。
     *
     * @return 配置文件对象
     */
    public T getConfig() {
        return config;
    }

    /**
     * 加载配置文件内容。
     */
    @Override
    public void loadConfig() {
        createConfigIfNotExists();
        // 存在则加载
        try {
            logger.info("正在加载配置文件: {}...", getConfigClass());
            String configContent = Files.readString(getConfigPath());
            config = JsonUtil.OBJECT_MAPPER.readValue(configContent, getConfigClass());
        } catch (IOException e) {
            logger.error("加载配置文件错误", e);
            PopUpUtil.errorAlert("错误", "读写错误", "加载配置文件错误!", null, null);
            throw new AppException(e);
        }
    }

    /**
     * 配置文件持久化。
     */
    @Override
    public void writeConfig() {
        createConfigIfNotExists();
        writeConfig(getConfig());
    }

    /**
     * 配置文件持久化。
     *
     * @param config 配置文件对象
     */
    @Override
    public void writeConfig(T config) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigPath().toString()))) {
            if (config == null) {
                config = generateDefaultConfig();
            }
            writer.write(JsonUtil.toJsonString(config));
        } catch (Exception e) {
            logger.error("", e);
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!", null, null);
        }
    }

    /**
     * 如果配置文件不存在则创建。
     */
    @Override
    public void createConfigIfNotExists() {
        Path configPath = getConfigPath();
        if (configPath.toFile().exists()) {
            return;
        }
        File directory = new File(getConfigDir());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        writeConfig(null);
    }

    /**
     * 获取配置文件路径。
     *
     * @return 配置文件路径
     */
    @Override
    public Path getConfigPath() {
        return Paths.get(getConfigDir(), getConfigName());
    }
}
