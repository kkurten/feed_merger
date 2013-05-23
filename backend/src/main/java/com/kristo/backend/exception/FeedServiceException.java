/**
 * 
 */
package com.kristo.backend.exception;

/**
 * @author kkurten
 * 
 */
public class FeedServiceException extends RuntimeException {
    private static final long serialVersionUID = -4903728373671720289L;

    public FeedServiceException(Throwable cause) {
        super(cause);
    }

    public FeedServiceException(String message, Throwable cause, Object... parameters) {
        super(String.format(message, parameters), cause);
    }

}
