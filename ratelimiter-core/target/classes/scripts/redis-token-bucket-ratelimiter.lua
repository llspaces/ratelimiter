local key = KEYS[1];
local limit = tonumber(KEYS[2]);
local step = tonumber(KEYS[3]);
local interval = tonumber(KEYS[4]);
local nowTime = tonumber(KEYS[5]);

local lastClearTimeKey = 'redis-ratelimiter-lastClearTime'..key
local lastClearTime = redis.call('GET', lastClearTimeKey);
local hasKey = redis.call('EXISTS', key);

if hasKey == 1 and lastClearTime then
    local diff = tonumber(nowTime) - tonumber(lastClearTime);
    local value = tonumber(redis.call('GET', key));
    if  diff >= interval then
            local maxValue = value + math.floor(diff / interval) * step;
            if maxValue > limit then
                value = limit;
            else
                value = maxValue;
            end
            redis.call('SET', lastClearTimeKey, nowTime);
            redis.call('SET', key, value);
    end
    if value <= 0 then
        return -1;
    else
        redis.call('DECR', key);
    end
else
    redis.call('SET', key, limit - 1);
    redis.call('SET', lastClearTimeKey, nowTime);
end
return 1;
