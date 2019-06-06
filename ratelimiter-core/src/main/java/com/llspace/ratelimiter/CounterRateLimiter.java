package com.llspace.ratelimiter;

import com.llspace.ratelimiter.utils.Constant;
import com.llspace.ratelimiter.utils.RateLimitAlgorithm;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>@filename CounterRateLimiter</p>
 * <p>
 * <p>@description 基于内存实现计数器限流</p>
 *
 * @author liyupeng
 * @version 1.0
 * @since 2019/6/5 11:16
 **/
@Component
public class CounterRateLimiter implements RateLimiter {

    private long timeStamp = System.currentTimeMillis();

    private AtomicInteger counter = new AtomicInteger(1);

    private Map<String, Object[]> keyMap = new HashMap<>();

    @Override
    public void rateLimit(String key, long limit, long refreshInterval, long tokenBucketTimeInterval, long tokenNumber) {
        if(key.contains(Constant.PREFIX)) {
            //非ALL类型限流
            if(keyMap.get(key) == null) {
                keyMap.put(key, new  Object[]{System.currentTimeMillis(), new AtomicInteger(1)});
            }else {
                keyMap.put(key, doRateLimit(key, limit, refreshInterval, (Long)keyMap.get(key)[0], (AtomicInteger)keyMap.get(key)[1]));
            }
            return;
        }
        doRateLimit(key, limit, refreshInterval, this.timeStamp, this.counter);
    }

    private Object[] doRateLimit(String key, long limit, long refreshInterval, long timeStamp, AtomicInteger counter){
        long nowTime = System.currentTimeMillis();
        if (nowTime < timeStamp + refreshInterval * 1000) {
            // 在refreshInterval内
            if(counter.get() <= Long.valueOf(limit).intValue()){
                //没有超出限制
                counter.incrementAndGet();
            } else {
                System.out.println(key + " ratelimit");
            }
        } else {
            // 超时后重置
            timeStamp = nowTime;
            counter = new AtomicInteger(1);
        }
        return new Object[]{timeStamp, counter};
    }

    @Override
    public String algorithm() {
        return RateLimitAlgorithm.COUNTER.getName();
    }
}
