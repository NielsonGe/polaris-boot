package org.polaris.framework.core.type;

import org.polaris.framework.core.lang.ValuedEnum;

/**
 * Inet Address Type
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public enum InetAddressType implements ValuedEnum<Byte> {
    /**
     * IPv4 network address type
     */
    IPv4((byte)0),
    /**
     * IPv6 network address type
     */
    IPv6((byte)1);

    /**
     * Enum value
     */
    protected final byte value;

    /**
     * Constructor
     * @param value Enum value
     */
    InetAddressType(byte value) {
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return value;
    }
}
