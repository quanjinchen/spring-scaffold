package dn.spring.scaffold.common.utils;

import dn.spring.scaffold.common.encryptor.Encryptor;
import dn.spring.scaffold.common.json.crypto.JsonCryptoModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper(false);

    private static volatile ObjectMapper cryptoObjectMapper;

    private static ObjectMapper createObjectMapper(boolean useCryptoModule) {
        JsonMapper.Builder builder = JsonMapper.builder()
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .addModule(new JavaTimeModule());
        if (useCryptoModule) {
            Encryptor encryptor = SpringUtils.getBean(Encryptor.class);
            if (encryptor == null) {
                throw new IllegalStateException("Encryptor bean not found");
            }
            builder.addModule(new JsonCryptoModule(encryptor));
        }
        return builder.build();
    }

    private static ObjectMapper getObjectMapper(boolean useCryptoModule) {
        if (!useCryptoModule) {
            return OBJECT_MAPPER;
        }
        if (cryptoObjectMapper == null) {
            synchronized (JsonUtils.class) {
                if (cryptoObjectMapper == null) {
                    cryptoObjectMapper = createObjectMapper(true);
                }
            }
        }
        return cryptoObjectMapper;
    }

    public static <T> String toJson(T bean) {
        return toJson(bean, false);
    }

    public static <T> String toJson(T bean, boolean useCryptoModule) {
        try {
            return getObjectMapper(useCryptoModule).writeValueAsString(bean);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to write json", exception);
        }
    }

    public static <T> T fromJson(String json, Class<T> beanClass) {
        return fromJson(json, beanClass, false);
    }

    public static <T> T fromJson(String json, Class<T> beanClass, boolean useCryptoModule) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return getObjectMapper(useCryptoModule).readValue(json, beanClass);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read json", exception);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> beanClass, boolean useCryptoModule) {
        try {
            return getObjectMapper(useCryptoModule).readValue(inputStream, beanClass);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read json stream", exception);
        }
    }

    public static <T> T fromJson(InputStream inputStream, Class<T> beanClass) {
        return fromJson(inputStream, beanClass, false);
    }

    public static <T> T fromJson(String json, Class<?> parametrized, Class<?>... parameterClasses) {
        return fromJson(json, parametrized, false, parameterClasses);
    }

    public static <T> T fromJson(String json, Class<?> parametrized, boolean useCryptoModule, Class<?>... parameterClasses) {
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            JavaType javaType = getObjectMapper(useCryptoModule).getTypeFactory()
                    .constructParametricType(parametrized, parameterClasses);
            return getObjectMapper(useCryptoModule).readValue(json, javaType);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read generic json", exception);
        }
    }

    public static <T> Map<String, Object> toMap(T bean) {
        return toMap(bean, false);
    }

    public static <T> Map<String, Object> toMap(T bean, boolean useCryptoModule) {
        if (bean == null) {
            return null;
        }
        String json = bean instanceof CharSequence ? bean.toString() : toJson(bean, useCryptoModule);
        return fromJson(json, Map.class, useCryptoModule);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return toList(json, clazz, false);
    }

    public static <T> List<T> toList(String json, Class<T> clazz, boolean useCryptoModule) {
        if (!StringUtils.hasText(json)) {
            return Collections.emptyList();
        }
        try {
            JavaType targetType = getObjectMapper(useCryptoModule).getTypeFactory()
                    .constructParametricType(List.class, clazz);
            return getObjectMapper(useCryptoModule).readValue(json, targetType);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read list json", exception);
        }
    }

    public static <T> T fromMap(Map<String, Object> source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        return fromJson(toJson(source), clazz);
    }
}
