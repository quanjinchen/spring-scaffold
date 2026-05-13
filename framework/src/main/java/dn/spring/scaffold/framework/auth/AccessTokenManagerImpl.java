package dn.spring.scaffold.framework.auth;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import dn.spring.scaffold.framework.redis.manager.RedisManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AccessTokenManagerImpl implements AccessTokenManager {

    private static final String ACCESS_TOKEN_CACHE_PREFIX = "auth:app:access-token:";

    @Resource
    private RedisManager redisManager;

    @Override
    public String getAccessToken(AccessTokenInfo accessTokenInfo, long expireSeconds) {
        String accessToken = generateAccessToken(accessTokenInfo.getAppId());
        redisManager.set(buildAccessTokenCacheKey(accessToken), accessTokenInfo, expireSeconds);
        return accessToken;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return StrUtil.isNotBlank(accessToken) && getAccessToken(accessToken) != null;
    }

    @Override
    public AccessTokenInfo getAccessToken(String accessToken) {
        if (StrUtil.isBlank(accessToken)) {
            return null;
        }
        return redisManager.get(buildAccessTokenCacheKey(accessToken), AccessTokenInfo.class);
    }

    private String buildAccessTokenCacheKey(String accessToken) {
        return ACCESS_TOKEN_CACHE_PREFIX + accessToken;
    }

    private String generateAccessToken(Long appId) {
        return "atk_" + appId + "_" + IdUtil.fastSimpleUUID();
    }
}
