/**
 * 
 */
package com.kristo.backend.exception;

/**
 * @author kkurten
 * 
 */
public class FeedReaderException extends RuntimeException {
    private static final long serialVersionUID = -4903728373671720289L;

    public FeedReaderException(Throwable cause) {
        super(cause);
    }

    public FeedReaderException(String message, Throwable cause, Object... parameters) {
        super(String.format(message, parameters), cause);
    }

}
