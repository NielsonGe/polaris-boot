package org.polaris.framework.core.exception;

import org.polaris.framework.core.lang.ValuedEnum;

/**
 * General Service Status
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public enum GeneralStatus implements ValuedEnum<Long> {
    /**
     * Success
     */
    OK(0L),
    /**
     * Param error
     */
    PARAM_ERROR(1L),
    /**
     * Snowflake worker id init error
     */
    SNOWFLAKE_WORKER_ID_ERROR(2L),
    /**
     * Snowflake max vibration offset init error
     */
    SNOWFLAKE_MAX_VIBRATION_OFFSET_ERROR(3L),
    /**
     * Snowflake max tolerate time difference milliseconds init error
     */
    SNOWFLAKE_MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS(4L),
    /**
     * Snowflake id generate error
     */
    SNOWFLAKE_ID_GENERATE_ERROR(5L);

    /**
     * Enum value
     */
    protected final long value;

    /**
     * Constructor
     * @param value Enum value
     */
    GeneralStatus(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return null;
    }
}
