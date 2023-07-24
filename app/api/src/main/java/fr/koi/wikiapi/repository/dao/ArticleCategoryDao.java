package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.dto.exception.article.NotFoundArticleException;
import fr.koi.wikiapi.repository.ArticleCategoryRepository;
import fr.koi.wikiapi.service.article_category.ArticleCategoryAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The DAO to manage article categories in the database.
 */
@Service
@RequiredArgsConstructor
public class ArticleCategoryDao {
    /**
     * The repository to manage article categories in the database.
     */
    private final ArticleCategoryRepository articleCategoryRepository;

    /**
     * The service to article category authentication.
     */
    private final ArticleCategoryAuthenticationService articleCategoryAuthenticationService;

    /**
     * Get an entity with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found entity
     */
    public ArticleCategoryEntity getById(final Long id) {
        var entity = this.articleCategoryRepository.findById(id).orElseThrow(() -> new NotFoundArticleException(id));

        this.articleCategoryAuthenticationService.checkReadRoles(entity);

        return entity;
    }

    /**
     * Save the specified entity.
     *
     * @param entity The entity to save
     */
    public void save(final ArticleCategoryEntity entity) {
        this.articleCategoryAuthenticationService.checkSaveRoles(entity);

        this.articleCategoryRepository.save(entity);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    public void delete(final Long id) {
        ArticleCategoryEntity entity = this.getById(id);

        this.articleCategoryAuthenticationService.checkDeleteRoles(entity);

        this.articleCategoryRepository.deleteById(id);
    }
}
