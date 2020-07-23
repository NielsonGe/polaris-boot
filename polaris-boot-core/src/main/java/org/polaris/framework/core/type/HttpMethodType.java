package org.polaris.framework.core.type;

import org.polaris.framework.core.lang.ValuedEnum;

/**
 * Http Method Type
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public enum HttpMethodType implements ValuedEnum<Byte> {
    /**
     * Get request type
     */
    GET((byte)0),
    /**
     * Post request type
     */
    POST((byte)1),
    /**
     * Put request type
     */
    PUT((byte)2),
    /**
     * Delete request type
     */
    DELETE((byte)3);

    /**
     * Enum value
     */
    protected final byte value;

    /**
     * Constructor
     * @param value Enum value
     */
    HttpMethodType(byte value) {
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return value;
    }
}
