package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.dto.exception.article.NotFoundArticleException;
import fr.koi.wikiapi.repository.ArticleRepository;
import fr.koi.wikiapi.service.article.ArticleAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The DAO to manage article in the database.
 */
@Service
@RequiredArgsConstructor
public class ArticleDao {
    /**
     * The repository to manage article in the database.
     */
    private final ArticleRepository articleRepository;

    /**
     * The service to article authentication.
     */
    private final ArticleAuthenticationService articleAuthenticationService;

    /**
     * Get an entity with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found entity
     */
    public ArticleEntity getById(final Long id) {
        ArticleEntity entity = this.articleRepository.findById(id).orElseThrow(() -> new NotFoundArticleException(id));

        this.articleAuthenticationService.checkReadRoles(entity);

        return entity;
    }

    /**
     * Save the specified entity.
     *
     * @param entity The entity to save
     */
    public void save(final ArticleEntity entity) {
        this.articleAuthenticationService.checkSaveRoles(entity);

        this.articleRepository.save(entity);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    public void delete(final Long id) {
        ArticleEntity entity = this.getById(id);

        this.articleAuthenticationService.checkDeleteRoles(entity);

        this.articleRepository.deleteById(id);
    }
}
