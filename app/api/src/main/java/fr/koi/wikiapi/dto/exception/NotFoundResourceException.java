package fr.koi.wikiapi.dto.exception;

import org.springframework.http.HttpStatus;

/**
 * The base class for all not found resource exception.
 */
public class NotFoundResourceException extends BaseException {
    /**
     * Create a new not found exception.
     */
    public NotFoundResourceException() {
        super(HttpStatus.NOT_FOUND, "error.not-found", "Not found REST resource");
    }
}
