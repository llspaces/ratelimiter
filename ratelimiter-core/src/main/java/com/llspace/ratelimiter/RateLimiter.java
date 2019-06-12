package com.llspace.ratelimiter;

/**
 * <p>@filename RateLimiter</p>
 * <p>
 * <p>@description 限流接口定义</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/4 11:35
 **/
public interface RateLimiter {

    public void rateLimit(String key, long limit, long refreshInterval, long tokenBucketTimeInterval, long tokenNumber);

    public String algorithm();
}
