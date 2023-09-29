package org.jcnc.jnotepad.common.manager;

import org.jcnc.jnotepad.model.entity.Cache;

import java.util.Map;

/**
 * 抽象缓存管理类
 *
 * @author gewuyou
 */
public abstract class AbstractCacheManager {
    /**
     * 缓存集合
     */
    protected Map<String, Cache<?>> caches;

    /**
     * 获取全局命名空间
     *
     * @return 全局命名空间
     */
    public abstract String getGlobalNamespace();

    /**
     * 获取缓存集合
     *
     * @return 缓存集合
     */

    public abstract Map<String, Cache<?>> getCaches();

    /**
     * 设置缓存集合
     *
     * @param caches 缓存集合
     */
    public abstract void setCaches(Map<String, Cache<?>> caches);

    /**
     * 添加缓存
     *
     * @param cache 缓存
     */
    public void addCache(Cache<?> cache) {
        String cacheKey = cache.getCacheKey();
        // 如果集合中已存在该缓存，则更新读写时间
        if (caches.containsKey(cacheKey)) {
            cache.setLastReadOrWriteTime(System.currentTimeMillis());
        }
        caches.put(cacheKey, cache);
    }

    /**
     * 获取缓存类
     *
     * @param cacheKey 缓存key
     * @return 缓存类
     */
    public Cache<?> getCache(String cacheKey) {
        if (caches.isEmpty()) {
            return null;
        }
        if (caches.containsKey(cacheKey)) {
            Cache<?> cache = caches.get(cacheKey);
            cache.setLastReadOrWriteTime(System.currentTimeMillis());
            return cache;
        }
        return null;
    }

    /**
     * 获取缓存数据
     *
     * @param cacheKey 缓存key
     * @return 缓存类
     */
    public Object getCacheData(String cacheKey) {
        Cache<?> cache = getCache(cacheKey);
        if (cache == null) {
            return null;
        }
        return cache.getCacheData();
    }

}
