package com.llspace.ratelimiter.aspect;

import com.llspace.ratelimiter.RateLimiter;
import com.llspace.ratelimiter.RateLimiterFactory;
import com.llspace.ratelimiter.annotation.MethodRateLimiter;
import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import com.llspace.ratelimiter.utils.RateLimiterUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>@filename MethodRateLimiterAspect</p>
 * <p>
 * <p>@description MethodRateLimiter注解切面类</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/4 17:01
 **/

@Component
@Aspect
public class MethodRateLimiterAspect {

    @Pointcut("@annotation(methodRateLimiter)")
    public void annotationPointcut(MethodRateLimiter methodRateLimiter) {

    }

    @Before("@annotation(methodRateLimiter)")
    public void doBefore(JoinPoint joinPoint, MethodRateLimiter methodRateLimiter) {
        String key = RateLimiterUtil.getKey(methodRateLimiter.algorithm().getName(), joinPoint,methodRateLimiter.type());
        RateLimiterFactory.getRateLimiter(methodRateLimiter.algorithm().getName()).rateLimit(key,methodRateLimiter.limit(),
            methodRateLimiter.refreshInterval(),methodRateLimiter.tokenBucketTimeInterval(), methodRateLimiter.tokenNumber());
    }
}
