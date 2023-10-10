package org.jcnc.jnotepad.api.core.controller.interfaces;

import java.nio.file.Path;

/**
 * 配置文件控制器接口
 * <p>
 * 该接口定义了配置文件相关的操作，包括加载、持久化、创建和获取配置文件路径等。
 * </p>
 *
 * @param <T> 配置文件类型
 * @author gewuyou
 */
public interface ConfigController<T> {
    /**
     * 加载配置文件内容
     * <p>
     * 从配置文件中加载配置信息。
     * </p>
     */
    void loadConfig();

    /**
     * 配置文件持久化
     * <p>
     * 将配置信息持久化到配置文件中。
     * </p>
     */
    void writeConfig();

    /**
     * 配置文件持久化
     * <p>
     * 将指定的配置对象持久化到配置文件中。
     * </p>
     *
     * @param config 配置文件对象
     */
    void writeConfig(T config);

    /**
     * 如果配置文件不存在则创建
     * <p>
     * 在需要的情况下创建配置文件，如果配置文件已存在，则不执行任何操作。
     * </p>
     */
    void createConfigIfNotExists();

    /**
     * 创建配置文件实体
     * <p>
     * 生成默认的配置文件实体对象，用于后续的序列化操作。
     * </p>
     *
     * @return 默认的配置文件实体
     * @apiNote 返回默认的配置文件实体用于序列化 JSON 数据。
     */
    T generateDefaultConfig();

    /**
     * 获取配置文件路径
     * <p>
     * 返回配置文件的路径。
     * </p>
     *
     * @return 配置文件路径
     */
    Path getConfigPath();
}
