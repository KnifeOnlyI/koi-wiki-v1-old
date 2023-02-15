package fr.koi.wikiapi.dto.exception;

import org.springframework.http.HttpStatus;

/**
 * The base class for all forbidden resource exception.
 */
public class ForbiddenException extends BaseException {
    /**
     * Create a new forbidden exception.
     */
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, "error.forbidden", "Forbidden access");
    }
}
