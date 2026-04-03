package dn.spring.scaffold.common.converter;

import dn.spring.scaffold.common.entity.EncryptField;
import dn.spring.scaffold.common.utils.JsonUtils;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface BaseConverter {

    String TO_STRING_LIST = "toStringList";
    String TO_LONG_LIST = "toLongList";
    String TO_LONG_SET = "toLongSet";
    String TO_JSON = "toJson";
    String TO_ENCRYPT_JSON = "toEncryptJson";
    String TO_ENCRYPT_FIELD = "toEncryptField";
    String TO_PLAIN_TEXT = "toPlainText";
    String TO_COMMA_SPLIT_STR = "toCommaSplitStr";
    String FROM_COMMA_SPLIT_STR = "fromCommaSplitStr";

    @Named(TO_STRING_LIST)
    default List<String> toStringList(String json) {
        return JsonUtils.toList(json, String.class);
    }

    @Named(TO_LONG_LIST)
    default List<Long> toLongList(String json) {
        return JsonUtils.toList(json, Long.class);
    }

    @Named(TO_LONG_SET)
    default Set<Long> toLongSet(String json) {
        return new LinkedHashSet<Long>(JsonUtils.toList(json, Long.class));
    }

    @Named(TO_JSON)
    default String toJson(Object obj) {
        return JsonUtils.toJson(obj);
    }

    @Named(TO_ENCRYPT_JSON)
    default String toEncryptJson(Object obj) {
        return JsonUtils.toJson(obj, true);
    }

    @Named(TO_PLAIN_TEXT)
    default String toPlainText(EncryptField encryptField) {
        return EncryptField.toPlainText(encryptField);
    }

    @Named(TO_ENCRYPT_FIELD)
    default EncryptField toEncryptField(String plainText) {
        if (!StringUtils.hasText(plainText)) {
            return null;
        }
        return new EncryptField(plainText);
    }

    @Named(TO_COMMA_SPLIT_STR)
    default String toCommaSplitStr(Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        return collection.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Named(FROM_COMMA_SPLIT_STR)
    default List<String> fromCommaSplitStr(String value) {
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        String[] items = value.split(",");
        List<String> result = new ArrayList<String>(items.length);
        for (String item : items) {
            if (StringUtils.hasText(item)) {
                result.add(item.trim());
            }
        }
        return result;
    }
}
