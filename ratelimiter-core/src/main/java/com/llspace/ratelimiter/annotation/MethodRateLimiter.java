package com.llspace.ratelimiter.annotation;

import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import com.llspace.ratelimiter.utils.RateLimitType;

import java.lang.annotation.*;

/**
 * <p>@filename MethodRateLimiter</p>
 * <p>
 * <p>@description 方法限流注解</p>
 *
 * @author liyupeng
 * @version 1.0
 * @since 2019/6/4 12:00
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodRateLimiter {
    /**
     *
     * @return RateLimitType 限流类型。默认值：ALL。可选值：ALL,IP,USER,CUSTOM
     */
    public RateLimitType type() default RateLimitType.ALL;

    public RateLimitAlgorithm algorithm() default RateLimitAlgorithm.REDIS_COUNTER;
    /**
     *
     * @return 限流次数。默认值10
     */
    public long limit() default 10;
    /**
     *
     * @return 限流时间间隔,以秒为单位。默认值60
     */
    public long refreshInterval() default 60;

    /**
     * 向令牌桶中添加数据的时间间隔,以秒为单位。默认值10秒
     */
    public long tokenBucketTimeInterval() default 10;
    /**
     * 每次为令牌桶中添加的令牌数量。默认值5个
     */
    public long tokenNumber() default 5;

}
