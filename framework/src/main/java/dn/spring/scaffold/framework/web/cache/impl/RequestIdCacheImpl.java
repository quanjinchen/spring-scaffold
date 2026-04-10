package dn.spring.scaffold.framework.web.cache.impl;

import dn.spring.scaffold.framework.redis.manager.RedisManager;
import dn.spring.scaffold.framework.web.cache.RequestIdCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RequestIdCacheImpl implements RequestIdCache {

    private static final String REQUEST_ID_CACHE_PREFIX = "request:id:";

    @Resource
    private RedisManager redisManager;

    @Override
    public boolean saveRequestId(String requestId, long expireSeconds) {
        String cacheKey = REQUEST_ID_CACHE_PREFIX + requestId;
        String existed = redisManager.get(cacheKey, String.class);
        if (existed != null) {
            return false;
        }
        redisManager.set(cacheKey, "1", expireSeconds);
        return true;
    }
}
