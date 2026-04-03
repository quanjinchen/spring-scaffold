package dn.spring.scaffold.framework.redis.manager;

import java.util.concurrent.TimeUnit;

public interface RedisManager {

    void set(String key, Object value);

    void set(String key, Object value, long timeoutSeconds);

    <T> T get(String key, Class<T> clazz);

    Boolean delete(String key);

    Boolean expire(String key, long timeout, TimeUnit timeUnit);
}
