package com.llspace.ratelimiter;

import com.llspace.ratelimiter.utils.Constant;
import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@filename RedisCounterRateLimiter</p>
 * <p>
 * <p>@description 基于redis实现计数限流</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/4 17:03
 **/
@Component
public class RedisCounterRateLimiter implements RateLimiter{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultRedisScript<Long> redisCounterScript;

    @Override
    public void rateLimit(String key, long limit, long refreshInterval, long tokenBucketTimeInterval, long tokenNumber) {
        List<String> keyList = new ArrayList();
        keyList.add(key);
        keyList.add(limit + "");
        keyList.add(refreshInterval + "");
        String result = redisTemplate.execute(redisCounterScript, keyList, keyList).toString();
        if(Constant.REDIS_ERROR_STATUS.equals(result)){
            System.out.println("ratelimit");
            //异常处理
        }
    }

    @Override
    public String algorithm() {
        return RateLimitAlgorithm.REDIS_COUNTER.getName();
    }
}
