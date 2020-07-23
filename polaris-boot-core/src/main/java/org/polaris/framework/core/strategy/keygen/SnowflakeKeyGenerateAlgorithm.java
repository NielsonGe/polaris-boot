package org.polaris.framework.core.strategy.keygen;

import org.polaris.framework.core.exception.GeneralStatus;
import org.polaris.framework.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Snowflake Key Generate Algorithm
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public final class SnowflakeKeyGenerateAlgorithm {
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeKeyGenerateAlgorithm.class);

    public static final long EPOCH;

    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    private static final long WORKER_ID = 0;

    private static final int DEFAULT_VIBRATION_VALUE = 1;

    private static final int MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = 10;

    private long workerId;

    private int maxVibrationOffset;

    private int maxTolerateTimeDifferenceMilliseconds;

    private int sequenceOffset = -1;

    private long sequence;

    private long lastMilliseconds;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    public SnowflakeKeyGenerateAlgorithm(Long workerId, Integer maxVibrationOffset, Integer maxTolerateTimeDifferenceMilliseconds) {
        this.workerId = getWorkerId(workerId);
        this.maxVibrationOffset = getMaxVibrationOffset(maxVibrationOffset);
        this.maxTolerateTimeDifferenceMilliseconds = getMaxTolerateTimeDifferenceMilliseconds(maxTolerateTimeDifferenceMilliseconds);
    }

    private long getWorkerId(Long workerId) {
        long result = 0;

        try {
            result = Long.valueOf(workerId);
        } catch (Exception ex) {
            logger.warn("worker id无效", ex);
            throw new ServiceException(GeneralStatus.SNOWFLAKE_WORKER_ID_ERROR);
        }

        if (!(result >= 0L && result < WORKER_ID_MAX_VALUE)) {
            logger.warn("worker id无效");
            throw new ServiceException(GeneralStatus.SNOWFLAKE_WORKER_ID_ERROR);
        }
        return result;
    }

    private int getMaxVibrationOffset(Integer maxVibrationOffset) {
        int result = 0;

        try {
            result = Integer.valueOf(maxVibrationOffset);
        } catch (Exception ex) {
            logger.warn("最大ID生成器时间偏移量无效", ex);
            throw new ServiceException(GeneralStatus.SNOWFLAKE_MAX_VIBRATION_OFFSET_ERROR);
        }

        if (!(result >= 0 && result <= SEQUENCE_MASK)) {
            logger.warn("最大ID生成器时间偏移量无效");
            throw new ServiceException(GeneralStatus.SNOWFLAKE_MAX_VIBRATION_OFFSET_ERROR);
        }
        return result;
    }

    private int getMaxTolerateTimeDifferenceMilliseconds(Integer maxTolerateTimeDifferenceMilliseconds) {
        int result = 0;

        try {
            result = Integer.valueOf(maxTolerateTimeDifferenceMilliseconds);
        } catch (Exception ex) {
            throw new ServiceException(GeneralStatus.SNOWFLAKE_MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS);
        }

        return result;
    }

    public synchronized long generateKey() throws Exception {
        long currentMilliseconds = System.currentTimeMillis();
        if (waitTolerateTimeDifferenceIfNeed(currentMilliseconds)) {
            currentMilliseconds = System.currentTimeMillis();
        }
        if (lastMilliseconds == currentMilliseconds) {
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMilliseconds = waitUntilNextTime(currentMilliseconds);
            }
        } else {
            vibrateSequenceOffset();
            sequence = sequenceOffset;
        }
        lastMilliseconds = currentMilliseconds;
        return ((currentMilliseconds - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    private boolean waitTolerateTimeDifferenceIfNeed(final long currentMilliseconds) throws Exception {
        if (lastMilliseconds <= currentMilliseconds) {
            return false;
        }
        long timeDifferenceMilliseconds = lastMilliseconds - currentMilliseconds;

        if (timeDifferenceMilliseconds < maxTolerateTimeDifferenceMilliseconds) {
            throw new ServiceException(GeneralStatus.SNOWFLAKE_ID_GENERATE_ERROR);
        }

        Thread.sleep(timeDifferenceMilliseconds);
        return true;
    }

    private long waitUntilNextTime(final long lastTime) {
        long result = System.currentTimeMillis();
        while (result <= lastTime) {
            result = System.currentTimeMillis();
        }
        return result;
    }

    private void vibrateSequenceOffset() {
        sequenceOffset = sequenceOffset >= maxVibrationOffset ? 0 : sequenceOffset + 1;
    }
}
