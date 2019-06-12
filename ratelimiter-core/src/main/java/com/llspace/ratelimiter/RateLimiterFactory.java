package com.llspace.ratelimiter;

import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>@filename RateLimiterFactory</p>
 * <p>
 * <p>@description TODO</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/5 14:09
 **/
@Component
public class RateLimiterFactory implements ApplicationContextAware{
    private static Map<String, RateLimiter> rateLimiterBeanMap;

    public static RateLimiter getRateLimiter(String algorithm){
        return rateLimiterBeanMap.get(algorithm);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RateLimiter> map = applicationContext.getBeansOfType(RateLimiter.class);
        rateLimiterBeanMap = new HashMap<>();
        map.forEach((key, value) -> rateLimiterBeanMap.put(value.algorithm(), value));
    }
}
