package org.jcnc.jnotepad.common.manager;

import org.jcnc.jnotepad.api.core.manager.AbstractCacheManager;

/**
 * 应用程序缓存管理类
 *
 * @author gewuyou
 */
public class ApplicationCacheManager extends AbstractCacheManager {

    private static final ApplicationCacheManager INSTANCE = new ApplicationCacheManager();

    private ApplicationCacheManager() {

    }

    public static ApplicationCacheManager getInstance() {
        return INSTANCE;
    }

    @Override
    public String getGlobalNamespace() {
        return "jcnc";
    }
}
