package org.jcnc.jnotepad.cache.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 缓存类
 *
 * @author gewuyou
 */
public class Cache {
    /**
     * 命名空间
     */
    @JsonIgnore
    private String namespace;
    /**
     * 组
     */
    @JsonIgnore
    private String group;
    /**
     * 缓存名称
     */
    @JsonIgnore
    private String name;

    /**
     * 缓存数据
     */
    private Object cacheData;
    /**
     * 过期时间<br/>如果过期时间为负数则永不过期
     */
    private Long expirationTime;
    /**
     * 上次读或写时间
     */
    private Long lastReadOrWriteTime;

    public Cache() {

    }

    public Cache(String namespace, String group, String name, Object cacheData, Long expirationTime) {
        this.namespace = namespace;
        this.group = group;
        this.name = name;
        this.cacheData = cacheData;
        this.expirationTime = expirationTime;
        this.lastReadOrWriteTime = System.currentTimeMillis();
    }

    /**
     * 生成缓存key
     *
     * @param namespace 命名空间
     * @param group     组
     * @param name      缓存名称
     * @return 缓存key
     */
    public static String getCacheKey(String namespace, String group, String name) {
        return namespace + "." + group + "." + name;
    }

    /**
     * 获取缓存key
     *
     * @return key
     */
    @JsonIgnore
    public String getCacheKey() {
        return getCacheKey(namespace, group, name);
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCacheData() {
        return cacheData;
    }

    public void setCacheData(Object cacheData) {
        this.cacheData = cacheData;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getLastReadOrWriteTime() {
        return lastReadOrWriteTime;
    }

    public void setLastReadOrWriteTime(Long lastReadOrWriteTime) {
        this.lastReadOrWriteTime = lastReadOrWriteTime;
    }
}
