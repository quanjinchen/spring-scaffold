package dn.spring.scaffold.framework.captcha;

import dn.spring.scaffold.framework.redis.manager.RedisManager;
import org.springframework.stereotype.Component;

@Component
public class CaptchaCacheImpl implements CaptchaCache {

    private static final String CAPTCHA_CACHE_PREFIX = "captcha:";

    private final RedisManager redisManager;

    public CaptchaCacheImpl(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @Override
    public void put(String uuid, String code, int expireSeconds) {
        redisManager.set(CAPTCHA_CACHE_PREFIX + uuid, code, expireSeconds);
    }

    @Override
    public String get(String uuid) {
        return redisManager.get(CAPTCHA_CACHE_PREFIX + uuid, String.class);
    }

    @Override
    public void remove(String uuid) {
        redisManager.delete(CAPTCHA_CACHE_PREFIX + uuid);
    }
}
