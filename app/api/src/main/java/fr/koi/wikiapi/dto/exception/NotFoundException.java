package fr.koi.wikiapi.dto.exception;

import org.springframework.http.HttpStatus;

/**
 * The base class for all not found exception.
 */
public abstract class NotFoundException extends BaseException {
    /**
     * Create a new not found exception.
     *
     * @param entity The entity name
     * @param id     The entity id
     */
    public NotFoundException(final String entity, final Object id) {
        super(
            HttpStatus.NOT_FOUND,
            String.format("error.%s.not-found", entity),
            String.format("Not existing %s entity with id %s", entity, id)
        );
    }
}
