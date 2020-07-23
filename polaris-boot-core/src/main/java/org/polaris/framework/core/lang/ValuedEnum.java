package org.polaris.framework.core.lang;

/**
 * Valued Enum Interface
 * @param <T> Enum value type
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public interface ValuedEnum<T> {

    /**
     * Get enum value
     * @return Enum value
     */
    T getValue();
}
