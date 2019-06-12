package com.llspace.ratelimiter.test;

import com.llspace.ratelimiter.annotation.MethodRateLimiter;
import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import com.llspace.ratelimiter.utils.RateLimitType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@filename TestController</p>
 * <p>
 * <p>@description TODO</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/4 18:46
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/testMethodRedisCounter")
    @MethodRateLimiter(refreshInterval = 30, limit = 3, algorithm = RateLimitAlgorithm.REDIS_COUNTER)
    public String testMethodRedisCounter(){
        return "testMethodRedisCounter";
    }

    @RequestMapping("/testMethodCounter")
    @MethodRateLimiter(refreshInterval = 30, limit = 3, algorithm = RateLimitAlgorithm.COUNTER)
    public String testMethodCounter(){
        return "testMethodCounter";
    }

    @RequestMapping("/testIpMethodCounter")
    @MethodRateLimiter(type = RateLimitType.IP, refreshInterval = 30, limit = 3, algorithm = RateLimitAlgorithm.COUNTER)
    public String testIpMethodCounter(){
        return " testIpMethodCounter";
    }

    @RequestMapping("/testMethodRedisTokenBucket")
    @MethodRateLimiter(tokenBucketTimeInterval = 10, tokenNumber = 3, algorithm = RateLimitAlgorithm.REDIS_TOKEN_BUCKET)
    public String testMethodRedisTokenBucket(){
        return "testMethodRedisTokenBucket";
    }

    @RequestMapping("/testMethodGuava")
    @MethodRateLimiter(algorithm = RateLimitAlgorithm.GUAVA, tokenNumber = 3)
    public String testMethodGuava(){
        return "testMethodGuava";
    }

}
