package com.llspace.ratelimiter.annotation;

import com.llspace.ratelimiter.config.EnableRateLimiterConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>@filename EnableRateLimiter</p>
 * <p>
 * <p>@description 开启限流功能注解</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/4 11:43
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnableRateLimiterConfiguration.class)
@Documented
@Inherited
public @interface EnableRateLimiter {
}
