package fr.koi.wikiapi.dto.exception.article;

import fr.koi.wikiapi.dto.exception.NotFoundException;

/**
 * Represent a not found article-category entity exception.
 */
public class NotFoundArticleCategoryException extends NotFoundException {
    /**
     * Create a new not found article-category entity exception.
     *
     * @param id The entity ID
     */
    public NotFoundArticleCategoryException(final Long id) {
        super("article-category", id);
    }
}
