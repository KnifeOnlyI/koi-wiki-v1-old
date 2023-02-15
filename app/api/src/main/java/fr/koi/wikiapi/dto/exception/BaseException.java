package fr.koi.wikiapi.dto.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The base class for all koi-wiki exception.
 */
@Getter
public abstract class BaseException extends RuntimeException {
    /**
     * The corresponding HTTP status.
     */
    private final HttpStatus status;

    /**
     * The key.
     */
    private final String key;

    /**
     * The detail message
     */
    private final String detail;

    /**
     * Create a new instance
     *
     * @param status The status
     * @param key    The key
     * @param detail The detail message
     */
    public BaseException(final HttpStatus status, final String key, final String detail) {
        this.status = status;
        this.key = key;
        this.detail = detail;
    }
}
