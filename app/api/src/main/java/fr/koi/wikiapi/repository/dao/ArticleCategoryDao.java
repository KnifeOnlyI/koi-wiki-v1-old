package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.dto.exception.NotFoundResourceException;
import fr.koi.wikiapi.dto.exception.article.NotFoundArticleCategoryException;
import fr.koi.wikiapi.repository.ArticleCategoryRepository;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.utils.TimeProvider;
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
    public ArticleCategoryEntity getById(final Long id) {
        if (this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)) {
            return this.articleCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundArticleCategoryException(id));
        } else if (this.userService.hasRole(Roles.ArticleCategory.READ)) {
            return this.articleCategoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundArticleCategoryException(id));
        } else {
            throw new NotFoundArticleCategoryException(id);
        }
    }

    /**
     * Save the specified entity.
     *
     * @param data The entity to save
     */
    public void save(final ArticleCategoryEntity data) {
        if (data.getId() == null) {
            if (!this.userService.hasRole(Roles.ArticleCategory.CREATE)) {
                throw new NotFoundResourceException();
            }
        } else {
            if (data.getDeletedAt() == null) {
                if (!this.userService.hasRole(Roles.ArticleCategory.UPDATE)) {
                    throw new NotFoundResourceException();
                }
            } else if (!this.userService.hasRole(Roles.ArticleCategory.UPDATE_DELETED)) {
                throw new NotFoundResourceException();
            }
        }

        this.articleCategoryRepository.save(data);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    public void delete(final Long id) {
        ArticleCategoryEntity entity = this.getById(id);

        if (entity.getDeletedAt() == null) {
            if (!this.userService.hasRole(Roles.ArticleCategory.DELETE)) {
                throw new NotFoundArticleCategoryException(id);
            }

            entity.setDeletedAt(TimeProvider.getInstance().nowZonedDateTime());
        } else {
            if (!this.userService.hasRole(Roles.ArticleCategory.DELETE_DELETED)) {
                throw new NotFoundArticleCategoryException(id);
            }

            this.articleCategoryRepository.deleteById(id);
        }
    }
}
