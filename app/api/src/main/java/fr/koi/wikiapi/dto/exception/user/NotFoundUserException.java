package fr.koi.wikiapi.dto.exception.user;

import fr.koi.wikiapi.dto.exception.NotFoundException;

/**
 * Represent a not found user entity exception.
 */
public class NotFoundUserException extends NotFoundException {
    /**
     * Create a new not found user entity exception.
     *
     * @param id The entity ID
     */
    public NotFoundUserException(final String id) {
        super("user", id);
    }
}
