package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.dto.exception.NotFoundResourceException;
import fr.koi.wikiapi.dto.exception.article.NotFoundArticleException;
import fr.koi.wikiapi.repository.ArticleRepository;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.utils.TimeProvider;
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
     * The service to manage users.
     */
    private final UserService userService;

    /**
     * Get an entity with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found entity
     */
    public ArticleEntity getById(final Long id) {
        if (this.userService.hasRole(Roles.Article.READ_DELETED)) {
            return this.articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundArticleException(id));
        } else if (this.userService.hasRole(Roles.Article.READ)) {
            return this.articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundArticleException(id));
        } else {
            throw new NotFoundArticleException(id);
        }
    }

    /**
     * Save the specified entity.
     *
     * @param data The entity to save
     */
    public void save(final ArticleEntity data) {
        if (data.getId() == null) {
            if (!this.userService.hasRole(Roles.Article.CREATE)) {
                throw new NotFoundResourceException();
            }
        } else {
            if (data.getDeletedAt() == null) {
                if (!this.userService.hasRole(Roles.Article.UPDATE)) {
                    throw new NotFoundResourceException();
                }
            } else if (!this.userService.hasRole(Roles.Article.UPDATE_DELETED)) {
                throw new NotFoundResourceException();
            }
        }

        this.articleRepository.save(data);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    public void delete(final Long id) {
        ArticleEntity entity = this.getById(id);

        if (entity.getDeletedAt() == null) {
            if (!this.userService.hasRole(Roles.Article.DELETE)) {
                throw new NotFoundArticleException(id);
            }

            entity.setDeletedAt(TimeProvider.getInstance().nowZonedDateTime());
        } else {
            if (!this.userService.hasRole(Roles.Article.DELETE_DELETED)) {
                throw new NotFoundArticleException(id);
            }

            this.articleRepository.deleteById(id);
        }
    }
}
