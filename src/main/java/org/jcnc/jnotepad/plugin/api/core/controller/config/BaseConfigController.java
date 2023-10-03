package org.jcnc.jnotepad.plugin.api.core.controller.config;

import org.jcnc.jnotepad.common.util.JsonUtil;
import org.jcnc.jnotepad.common.util.LogUtil;
import org.jcnc.jnotepad.common.util.PopUpUtil;
import org.jcnc.jnotepad.controller.interfaces.ConfigController;
import org.jcnc.jnotepad.exception.AppException;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 基本配置文件控制器抽象类
 *
 * @author gewuyou
 */
public abstract class BaseConfigController<T> implements ConfigController<T> {

    protected T config;
    Logger logger = LogUtil.getLogger(this.getClass());

    /**
     * 获取配置文件Class类
     *
     * @return 配置文件Class类
     */
    protected abstract Class<T> getConfigClass();

    /**
     * 生成默认的配置文件
     *
     * @return 默认的配置文件
     */
    protected abstract T generateDefaultConfig();

    /**
     * 获取配置文件名称
     *
     * @return 配置文件名称
     */
    protected abstract String getConfigName();

    /**
     * 获取配置文件文件夹路径
     *
     * @return 配置文件夹路径
     */
    protected abstract String getConfigDir();

    /**
     * 获取配置文件类
     *
     * @return 获取配置文件类
     */
    public T getConfig() {
        return config;
    }

    public void setConfig(T config) {
        this.config = config;
    }

    /**
     * 加载配置文件内容
     */
    @Override
    public void loadConfig() {
        createConfigIfNotExists();
        // 存在则加载
        try {
            logger.info("正在加载配置文件:{}...", getConfigClass());
            String configContent = Files.readString(getConfigPath());
            config = JsonUtil.OBJECT_MAPPER.readValue(configContent, getConfigClass());
        } catch (IOException e) {
            logger.error("加载配置文件错误", e);
            PopUpUtil.errorAlert("错误", "读写错误", "加载配置文件错误!", null, null);
            throw new AppException(e);
        }
    }

    /**
     * 配置文件持久化
     */
    @Override
    public void writeConfig() {
        createConfigIfNotExists();
        writeConfig(getConfig());
    }

    /**
     * 配置文件持久化
     *
     * @param config 配置文件对象
     */
    @Override
    public void writeConfig(T config) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getConfigPath().toString()))) {
            if (config == null) {
                config = createConfigJson();
            }
            writer.write(JsonUtil.toJsonString(config));
        } catch (Exception e) {
            logger.error("", e);
            PopUpUtil.errorAlert("错误", "读写错误", "配置文件读写错误!", null, null);
        }
    }

    /**
     * 如果配置文件不存在则创建
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
     * 创建配置文件json实体
     *
     * @return 默认的配置文件实体
     * @apiNote 返回默认的配置文件实体用于序列化jso
     */
    @Override
    public T createConfigJson() {
        return generateDefaultConfig();
    }

    /**
     * 获取配置文件路径
     *
     * @return 配置文件路径
     */
    @Override
    public Path getConfigPath() {
        return Paths.get(getConfigDir(), getConfigName());
    }
}
