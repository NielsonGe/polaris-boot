package org.polaris.framework.core.exception;

import org.polaris.framework.core.lang.ValuedEnum;

/**
 * Service Exception
 *
 * @author Nielson Ge
 * @since 0.0.1.0
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 2647416286007593357L;

    /**
     * Status enum
     */
    protected ValuedEnum<Long> status;

    /**
     * Exception param
     */
    protected Object[] params;

    /**
     * Constructor
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructor
     * @param status Status enum
     */
    public ServiceException(ValuedEnum<Long> status) {
        super();
        this.status = status;
    }

    /**
     * Constructor
     * @param status Status enum
     * @param cause Exception cause
     */
    public ServiceException(ValuedEnum<Long> status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    /**
     * Get status
     * @return Status enum
     */
    public ValuedEnum<Long> getStatus() {
        return status;
    }
}
