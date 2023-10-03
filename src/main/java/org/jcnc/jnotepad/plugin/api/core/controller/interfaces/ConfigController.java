package org.jcnc.jnotepad.plugin.api.core.controller.interfaces;

import java.nio.file.Path;

/**
 * 配置文件控制器接口
 *
 * @author gewuyou
 */
public interface ConfigController<T> {
    /**
     * 加载配置文件内容
     */
    void loadConfig();

    /**
     * 配置文件持久化
     */
    void writeConfig();

    /**
     * 配置文件持久化
     *
     * @param config 配置文件对象
     */
    void writeConfig(T config);

    /**
     * 如果配置文件不存在则创建
     */
    void createConfigIfNotExists();

    /**
     * 创建配置文件实体
     *
     * @return 默认的配置文件实体
     * @apiNote 返回默认的配置文件实体用于序列化json
     */
    T generateDefaultConfig();

    /**
     * 获取配置文件路径
     *
     * @return 配置文件路径
     */
    Path getConfigPath();
}
