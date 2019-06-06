package com.llspace.ratelimiter.utils;

/**
 * <p>@filename Constant</p>
 * <p>
 * <p>@description 静态常量类</p>
 *
 * @author liyupeng
 * @version 1.0
 * @since 2019/6/4 11:37
 **/
public class Constant {

    public static final String PREFIX = "{ratelimiter}";
    /**
     * redis集群模式指定slot的hash tag
     */
    public static final String REDIS_PREFIX = "{redis-ratelimiter}";

    /**
     * 自定义拦截方式时的key
     */
    public static final String CUSTOM = "rateLimiter-custom";

    public static final String REDIS_ERROR_STATUS = "-1";
}
