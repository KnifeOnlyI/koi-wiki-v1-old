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
        ArticleEntity entity;

        if (this.userService.hasRole(Roles.Article.READ_DELETED)) {
            entity = this.articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundArticleException(id));
        } else if (this.userService.hasRole(Roles.Article.READ)) {
            entity = this.articleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundArticleException(id));
        } else {
            throw new NotFoundArticleException(id);
        }

        if (!this.userService.getUserId().equals(entity.getAuthorId())) {
            if (entity.getDeletedAt() != null && !this.userService.hasRole(Roles.Article.READ_OTHER_DELETED)) {
                throw new NotFoundArticleException(id);
            } else if (entity.getIsArchived() != null && !this.userService.hasRole(Roles.Article.READ_OTHER_ARCHIVED)) {
                throw new NotFoundArticleException(id);
            }
        }

        return entity;
    }

    /**
     * Save the specified entity.
     *
     * @param entity The entity to save
     */
    public void save(final ArticleEntity entity) {
        if (entity.getId() == null) {
            if (!this.userService.hasRole(Roles.Article.CREATE)) {
                throw new NotFoundResourceException();
            }
        } else {
            if (entity.getDeletedAt() == null) {
                if (!this.userService.hasRole(Roles.Article.UPDATE)) {
                    throw new NotFoundArticleException(entity.getId());
                }
            } else if (!this.userService.hasRole(Roles.Article.UPDATE_DELETED)) {
                throw new NotFoundArticleException(entity.getId());
            }

            if (!this.userService.getUserId().equals(entity.getAuthorId())) {
                if (entity.getIsArchived() && !this.userService.hasRole(Roles.Article.UPDATE_OTHER_ARCHIVED)) {
                    throw new NotFoundArticleException(entity.getId());
                } else if (entity.getDeletedAt() != null && !this.userService.hasRole(Roles.Article.UPDATE_OTHER_DELETED)) {
                    throw new NotFoundArticleException(entity.getId());
                }
            }
        }

        this.articleRepository.save(entity);
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

            if (!this.userService.getUserId().equals(entity.getAuthorId())) {
                if (!this.userService.hasRole(Roles.Article.DELETE_OTHER)) {
                    throw new NotFoundArticleException(id);
                }
            }

            entity.setDeletedAt(TimeProvider.getInstance().nowZonedDateTime());
        } else {
            if (!this.userService.hasRole(Roles.Article.DELETE_DELETED)) {
                throw new NotFoundArticleException(id);
            }

            if (!this.userService.getUserId().equals(entity.getAuthorId())) {
                if (!this.userService.hasRole(Roles.Article.DELETE_OTHER_DELETED)) {
                    throw new NotFoundArticleException(id);
                }
            }

            this.articleRepository.deleteById(id);
        }
    }
}
