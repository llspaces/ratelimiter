package com.llspace.ratelimiter.utils;

/**
 * <p>@filename RateLimitAlgorithm</p>
 * <p>
 * <p>@description 限流算法枚举</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/5 14:11
 **/
public enum RateLimitAlgorithm {
    COUNTER("counter"),

    REDIS_COUNTER("redisCounter"),

    REDIS_TOKEN_BUCKET("redisTokenBucket"),

    GUAVA("guava");

    private String name;

    public String getName(){
        return name;
    }

    RateLimitAlgorithm(String name) {
        this.name = name;
    }
}
