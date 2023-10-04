package org.jcnc.jnotepad.cache.enums;

/**
 * 缓存过期时间枚举
 *
 * @author gewuyou
 */
public enum CacheExpirationTime {
    /**
     * 一小时
     */
    ONE_HOUR(60 * 60 * 1000L),
    /**
     * 一天
     */
    ONE_DAY(24 * ONE_HOUR.value),
    /**
     * 一周
     */
    ONE_WEEK(7 * ONE_DAY.value),
    /**
     * 一月
     */
    ONE_MONTH(30 * ONE_DAY.value),
    /**
     * 一年
     */
    ONE_YEAR(365 * ONE_DAY.value),

    /**
     * 永不过期
     */
    NEVER_EXPIRES(-1L);
    private final Long value;

    CacheExpirationTime(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}
