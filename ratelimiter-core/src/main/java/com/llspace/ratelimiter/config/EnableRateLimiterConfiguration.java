package com.llspace.ratelimiter.config;

import com.llspace.ratelimiter.CounterRateLimiter;
import com.llspace.ratelimiter.RateLimiter;
import com.llspace.ratelimiter.RedisCounterRateLimiter;
import com.llspace.ratelimiter.utils.Constant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

/**
 * <p>@filename EnableRateLimiterConfiguration</p>
 * <p>
 * <p>@description ratelimiter项目配置类(支持spring项目配置管理)</p>
 *
 * @author liyupeng
 * @version 1.0
 * @since 2019/6/4 11:40
 **/
@Configuration
@ComponentScan(basePackages = "com.llspace.ratelimiter")
public class EnableRateLimiterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public DefaultRedisScript<Long> redisCounterScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/redis-counter-ratelimiter.lua")));
        return redisScript;
    }

    @Bean
    public DefaultRedisScript<Long> redisTokenBucketScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/redis-token-bucket-ratelimiter.lua")));
        return redisScript;
    }

}
