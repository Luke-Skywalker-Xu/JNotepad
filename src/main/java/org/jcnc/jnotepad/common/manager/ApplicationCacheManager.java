package org.jcnc.jnotepad.common.manager;

import org.jcnc.jnotepad.model.entity.Cache;

import java.util.Map;

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
    public Map<String, Cache> getCaches() {
        return caches;
    }

    @Override
    public void setCaches(Map<String, Cache> caches) {
        this.caches = caches;
    }

    @Override
    public String getGlobalNamespace() {
        return "jcnc";
    }
}
