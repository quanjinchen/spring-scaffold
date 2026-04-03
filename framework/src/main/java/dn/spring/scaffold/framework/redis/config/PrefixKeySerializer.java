package dn.spring.scaffold.framework.redis.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

public class PrefixKeySerializer implements RedisSerializer<String> {

    private final String prefix;

    private final StringRedisSerializer delegate = new StringRedisSerializer(StandardCharsets.UTF_8);

    public PrefixKeySerializer(String prefix) {
        this.prefix = prefix == null ? "" : prefix;
    }

    @Override
    public byte[] serialize(String value) {
        return delegate.serialize(prefix + value);
    }

    @Override
    public String deserialize(byte[] bytes) {
        String value = delegate.deserialize(bytes);
        if (value == null) {
            return null;
        }
        if (prefix.isEmpty() || !value.startsWith(prefix)) {
            return value;
        }
        return value.substring(prefix.length());
    }
}
