package org.polaris.framework.core.util;

import org.polaris.framework.core.lang.ValuedEnum;

import java.util.stream.Stream;

/**
 * Enum utility
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public abstract class EnumUtils {

    /**
     * Get enum type according to enum value
     * @param clazz Enum class
     * @param value Enum value
     * @param <T> Enum type
     * @param <U> Enum type
     * @return
     */
    public static <T extends ValuedEnum<U>, U> T valueOf(Class<T> clazz, U value) {
        if (clazz == null) {
            return null;
        }

        return Stream.of(clazz.getEnumConstants())
                .filter(x -> x.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    /**
     * Valid enum value
     * @param clazz Enum class
     * @param value Enum value
     * @param <T> Enum type
     * @param <U> Enum type
     * @return Valid result
     */
    public static <T extends ValuedEnum<U>, U> boolean isValid(Class<T> clazz, U value) {
        return valueOf(clazz, value) != null;
    }
}
