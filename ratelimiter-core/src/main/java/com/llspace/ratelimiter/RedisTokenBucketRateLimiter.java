package com.llspace.ratelimiter;

import com.llspace.ratelimiter.utils.Constant;
import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@filename RedisTokenBucketRateLimiter</p>
 * <p>
 * <p>@description 基于redis的令牌桶限流算法</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/5 17:40
 **/
@Component
public class RedisTokenBucketRateLimiter implements RateLimiter {

    @Autowired private RedisTemplate redisTemplate;

    @Autowired private DefaultRedisScript<Long> redisTokenBucketScript;

    @Override
    public void rateLimit(String key, long limit, long refreshInterval, long tokenBucketTimeInterval, long tokenNumber) {
        List<String> keyList = new ArrayList();
        keyList.add(key);
        keyList.add(limit + "");
        keyList.add(tokenNumber + "");
        keyList.add(tokenBucketTimeInterval + "");
        keyList.add(System.currentTimeMillis() / 1000 + "");
        String result = redisTemplate.execute(redisTokenBucketScript, keyList, keyList).toString();
        if (Constant.REDIS_ERROR_STATUS.equals(result)) {
            System.out.println("ratelimit");
        }
    }

    @Override public String algorithm() {
        return RateLimitAlgorithm.REDIS_TOKEN_BUCKET.getName();
    }
}
