package org.polaris.framework.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Object Utility
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public abstract class ObjectUtils {

    /**
     * Reflect all field of object. Return subclass field, if
     * field has been override in subclass.
     *
     * @param clazz Object class
     * @return Object field map
     */
    public static Map<String, Field> getFields(Class clazz) {
        if (clazz == null) {
            return new HashMap<>();
        }

        Map<String, Field> fieldMap = getFields(clazz.getSuperclass());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            fieldMap.put(field.getName(), field);
        }

        return fieldMap;
    }
}
