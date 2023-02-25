package fr.koi.wikiapi.dto.exception.article;

import fr.koi.wikiapi.dto.exception.NotFoundException;

/**
 * Represent a not found article entity exception.
 */
public class NotFoundArticleException extends NotFoundException {
    /**
     * Create a new not found article entity exception.
     *
     * @param id The entity ID
     */
    public NotFoundArticleException(final Long id) {
        super("article", id);
    }
}
