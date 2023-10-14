package org.jcnc.jnotepad.app.common.manager;

import org.jcnc.jnotepad.api.core.manager.AbstractCacheManager;

/**
 * 应用程序缓存管理类。
 * <p>
 * 该类用于管理应用程序中的缓存数据。
 * </p>
 *
 * @author gewuyou
 */
public class ApplicationCacheManager extends AbstractCacheManager {

    private static final ApplicationCacheManager INSTANCE = new ApplicationCacheManager();

    private ApplicationCacheManager() {

    }

    /**
     * 获取 ApplicationCacheManager 的实例。
     *
     * @return ApplicationCacheManager 实例
     */
    public static ApplicationCacheManager getInstance() {
        return INSTANCE;
    }

    /**
     * 获取全局命名空间。
     *
     * @return 全局命名空间字符串
     */
    @Override
    public String getGlobalNamespace() {
        return "jcnc";
    }
}
