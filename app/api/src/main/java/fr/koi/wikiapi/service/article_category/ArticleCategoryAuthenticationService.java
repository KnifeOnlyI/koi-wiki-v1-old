package fr.koi.wikiapi.service.article_category;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategorySearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCategoryAuthenticationService {
    private final UserService userService;

    /**
     * Check search article category roles of the logged user;
     *
     * @param criteria The criteria
     */
    public void checkSearchRoles(final ArticleCategorySearchCriteria criteria) {
        if (!this.userService.hasRole(Roles.ArticleCategory.SEARCH)) {
            throw new ForbiddenException();
        }

        if (criteria.isDeleted() && !this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)) {
            throw new ForbiddenException();
        }
    }

    public void checkReadRoles(final ArticleCategoryEntity entity) {
        if (entity.getDeletedAt() != null) {
            // Check read deleted operations roles

            if (!this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)) {
                throw new ForbiddenException();
            }
        } else {
            // Check read operations roles

            if (!this.userService.hasRole(Roles.ArticleCategory.READ)) {
                throw new ForbiddenException();
            }
        }
    }

    public void checkSaveRoles(final ArticleCategoryEntity entity) {
        if (entity.getId() == null && !this.userService.hasRole(Roles.ArticleCategory.CREATE)) {
            throw new ForbiddenException();
        }

        if (entity.getId() == null) {
            return;
        }

        if (entity.getDeletedAt() != null) {
            // Check update deleted operations roles

            if (!this.userService.hasRole(Roles.ArticleCategory.UPDATE_DELETED)) {
                throw new ForbiddenException();
            }
        } else {
            // Check update operations roles

            if (!this.userService.hasRole(Roles.ArticleCategory.UPDATE)) {
                throw new ForbiddenException();
            }
        }
    }

    public void checkDeleteRoles(final ArticleCategoryEntity entity) {
        if (entity.getDeletedAt() != null) {
            // Check delete deleted operations roles

            if (!this.userService.hasRole(Roles.ArticleCategory.DELETE_DELETED)) {
                throw new ForbiddenException();
            }
        } else {
            // Check delete operations roles

            if (!this.userService.hasRole(Roles.ArticleCategory.DELETE)) {
                throw new ForbiddenException();
            }
        }
    }
}
