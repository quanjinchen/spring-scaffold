package dn.spring.scaffold.common.assertion;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 业务断言接口。
 * 允许 {@code ResultCode} 直接实现本接口，
 * 这样业务侧可以通过 {@code ResultCode.xxx.assert...(...)} 的方式直接完成校验并抛出异常，
 * 避免重复手写 {@code throw} 逻辑。
 */
public interface Assert {

    /**
     * 创建异常实例。
     *
     * @param args message 占位符对应的参数列表
     * @return 运行时异常
     */
    RuntimeException newException(Object... args);

    /**
     * 创建异常实例，并携带原始异常。
     *
     * @param cause 原始异常
     * @param args  message 占位符对应的参数列表
     * @return 运行时异常
     */
    RuntimeException newException(Throwable cause, Object... args);

    /**
     * 断言对象非空。如果对象为空，则抛出异常。
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    /**
     * 断言对象非空。如果对象为空，则抛出异常。
     *
     * @param obj  待判断对象
     * @param args message 占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

    /**
     * 断言集合为空。如果集合非空，则抛出异常。
     *
     * @param collection 待判断集合
     * @param <E> 集合元素类型
     */
    default <E> void assertIsEmpty(Collection<E> collection) {
        if (collection != null && !collection.isEmpty()) {
            throw newException();
        }
    }

    /**
     * 断言集合为空。如果集合非空，则抛出异常。
     *
     * @param collection 待判断集合
     * @param args message 占位符对应的参数列表
     * @param <E> 集合元素类型
     */
    default <E> void assertIsEmpty(Collection<E> collection, Object... args) {
        if (collection != null && !collection.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * 断言字符串为空串。如果字符串非空，则抛出异常。
     *
     * @param str 待判断字符串
     */
    default void assertIsEmpty(String str) {
        if (isNotBlank(str)) {
            throw newException();
        }
    }

    /**
     * 断言字符串不为空串。如果字符串为空，则抛出异常。
     *
     * @param str 待判断字符串
     */
    default void assertNotEmpty(String str) {
        if (!isNotBlank(str)) {
            throw newException();
        }
    }

    /**
     * 断言字符串为空串。如果字符串非空，则抛出异常。
     *
     * @param str 待判断字符串
     * @param args message 占位符对应的参数列表
     */
    default void assertIsEmpty(String str, Object... args) {
        if (isNotBlank(str)) {
            throw newException(args);
        }
    }

    /**
     * 断言字符串不为空串。如果字符串为空，则抛出异常。
     *
     * @param str 待判断字符串
     * @param args message 占位符对应的参数列表
     */
    default void assertNotEmpty(String str, Object... args) {
        if (!isNotBlank(str)) {
            throw newException(args);
        }
    }

    /**
     * 断言数组不为空。如果数组为空，则抛出异常。
     *
     * @param arrays 待判断数组
     */
    default void assertNotEmpty(Object[] arrays) {
        if (arrays == null || arrays.length == 0) {
            throw newException();
        }
    }

    /**
     * 断言数组不为空。如果数组为空，则抛出异常。
     *
     * @param arrays 待判断数组
     * @param args message 占位符对应的参数列表
     */
    default void assertNotEmpty(Object[] arrays, Object... args) {
        if (arrays == null || arrays.length == 0) {
            throw newException(args);
        }
    }

    /**
     * 断言集合不为空。如果集合为空，则抛出异常。
     *
     * @param collection 待判断集合
     */
    default void assertNotEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            throw newException();
        }
    }

    /**
     * 断言集合不为空。如果集合为空，则抛出异常。
     *
     * @param collection 待判断集合
     * @param args message 占位符对应的参数列表
     */
    default void assertNotEmpty(Collection<?> collection, Object... args) {
        if (collection == null || collection.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * 断言 Map 不为空。如果 Map 为空，则抛出异常。
     *
     * @param map 待判断 Map
     */
    default void assertNotEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            throw newException();
        }
    }

    /**
     * 断言 Map 不为空。如果 Map 为空，则抛出异常。
     *
     * @param map 待判断 Map
     * @param args message 占位符对应的参数列表
     */
    default void assertNotEmpty(Map<?, ?> map, Object... args) {
        if (map == null || map.isEmpty()) {
            throw newException(args);
        }
    }

    /**
     * 断言表达式为 false。如果表达式为 true，则抛出异常。
     *
     * @param expression 待判断布尔值
     */
    default void assertIsFalse(boolean expression) {
        if (expression) {
            throw newException();
        }
    }

    /**
     * 断言表达式为 false。如果表达式为 true，则抛出异常。
     *
     * @param expression 待判断布尔值
     * @param args message 占位符对应的参数列表
     */
    default void assertIsFalse(boolean expression, Object... args) {
        if (expression) {
            throw newException(args);
        }
    }

    /**
     * 断言表达式为 true。如果表达式为 false，则抛出异常。
     *
     * @param expression 待判断布尔值
     */
    default void assertIsTrue(boolean expression) {
        if (!expression) {
            throw newException();
        }
    }

    /**
     * 断言表达式为 true。如果表达式为 false，则抛出异常。
     *
     * @param expression 待判断布尔值
     * @param args message 占位符对应的参数列表
     */
    default void assertIsTrue(boolean expression, Object... args) {
        if (!expression) {
            throw newException(args);
        }
    }

    /**
     * 断言对象为空。如果对象不为空，则抛出异常。
     *
     * @param obj 待判断对象
     */
    default void assertIsNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }

    /**
     * 断言对象为空。如果对象不为空，则抛出异常。
     *
     * @param obj 待判断对象
     * @param args message 占位符对应的参数列表
     */
    default void assertIsNull(Object obj, Object... args) {
        if (obj != null) {
            throw newException(args);
        }
    }

    /**
     * 直接抛出异常。
     */
    default void assertFail() {
        throw newException();
    }

    /**
     * 直接抛出异常。
     *
     * @param args message 占位符对应的参数列表
     */
    default void assertFail(Object... args) {
        throw newException(args);
    }

    /**
     * 直接抛出异常，并附带原始异常。
     *
     * @param cause 原始异常
     */
    default void assertFail(Throwable cause) {
        throw newException(cause);
    }

    /**
     * 直接抛出异常，并附带原始异常。
     *
     * @param cause 原始异常
     * @param args message 占位符对应的参数列表
     */
    default void assertFail(Throwable cause, Object... args) {
        throw newException(cause, args);
    }

    /**
     * 断言两个对象相等。如果不相等，则抛出异常。
     *
     * @param o1 待判断对象
     * @param o2 待判断对象
     */
    default void assertEquals(Object o1, Object o2) {
        if (!Objects.equals(o1, o2)) {
            throw newException();
        }
    }

    /**
     * 断言两个对象相等。如果不相等，则抛出异常。
     *
     * @param o1 待判断对象
     * @param o2 待判断对象
     * @param args message 占位符对应的参数列表
     */
    default void assertEquals(Object o1, Object o2, Object... args) {
        if (!Objects.equals(o1, o2)) {
            throw newException(args);
        }
    }

    /**
     * 断言两个对象不相等。如果相等，则抛出异常。
     *
     * @param o1 待判断对象
     * @param o2 待判断对象
     */
    default void assertNotEquals(Object o1, Object o2) {
        if (Objects.equals(o1, o2)) {
            throw newException();
        }
    }

    /**
     * 断言元素在列表中。如果不在列表中，则抛出异常。
     *
     * @param element 待判断元素
     * @param list 待判断列表
     * @param <T> 元素类型
     */
    default <T> void assertIn(T element, List<T> list) {
        if (list == null || !list.contains(element)) {
            throw newException();
        }
    }

    /**
     * 断言元素不在列表中。如果在列表中，则抛出异常。
     *
     * @param element 待判断元素
     * @param list 待判断列表
     * @param <T> 元素类型
     */
    default <T> void assertNotIn(T element, List<T> list) {
        if (list != null && list.contains(element)) {
            throw newException();
        }
    }

    /**
     * 判断字符串是否非空白。
     *
     * @param str 待判断字符串
     * @return 是否非空白
     */
    default boolean isNotBlank(String str) {
        return str != null && !"".equals(str.trim());
    }
}
