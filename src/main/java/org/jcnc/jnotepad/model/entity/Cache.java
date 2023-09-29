package org.jcnc.jnotepad.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 缓存类
 *
 * @author gewuyou
 */
public class Cache<T> {
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
    private String name;

    /**
     * 缓存数据
     */
    private T cacheData;
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

    public Cache(String namespace, String group, String name, T cacheData, Long expirationTime) {
        this.namespace = namespace;
        this.group = group;
        this.name = name;
        this.cacheData = cacheData;
        this.expirationTime = expirationTime;
        this.lastReadOrWriteTime = System.currentTimeMillis();
    }

    /**
     * 根据缓存key
     *
     * @return key
     */
    public String getCacheKey() {
        return this.getNamespace() + "." + this.getGroup() + "." + this.getName();
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

    public T getCacheData() {
        return cacheData;
    }

    public void setCacheData(T cacheData) {
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
