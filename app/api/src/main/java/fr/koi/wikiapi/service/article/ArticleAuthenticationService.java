package fr.koi.wikiapi.service.article;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article.ArticleSearchCriteria;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleAuthenticationService {
    private final UserService userService;

    public void checkReadRoles(final ArticleEntity entity) {
        boolean loggedUserOwnArticle = StringUtils.equalsIgnoreCase(
            this.userService.getUserId(),
            entity.getAuthorId()
        );

        if (entity.getDeletedAt() != null) {
            // Check read deleted operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.READ_DELETED)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.READ_OTHER_DELETED)) {
                throw new ForbiddenException();
            }
        } else if (BooleanUtils.isTrue(entity.getIsArchived())) {
            // Check read archived operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.READ_ARCHIVED)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.READ_OTHER_ARCHIVED)) {
                throw new ForbiddenException();
            }
        } else {
            // Check read operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.READ)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.READ_OTHER)) {
                throw new ForbiddenException();
            }
        }
    }

    /**
     * Check save article roles of the logged user.
     *
     * @param entity The entity to save
     */
    public void checkSaveRoles(final ArticleEntity entity) {
        // Check create operations roles

        if (entity.getId() == null && !this.userService.hasRole(Roles.Article.CREATE)) {
            throw new ForbiddenException();
        }

        if (entity.getId() == null) {
            return;
        }

        var loggedUserOwnArticle = StringUtils.equalsIgnoreCase(this.userService.getUserId(), entity.getAuthorId());

        if (entity.getDeletedAt() != null) {
            // Check update deleted operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.UPDATE_DELETED)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.UPDATE_OTHER_DELETED)) {
                throw new ForbiddenException();
            }
        } else if (BooleanUtils.isTrue(entity.getIsArchived())) {
            // Check update archived operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.UPDATE_ARCHIVED)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.UPDATE_OTHER_ARCHIVED)) {
                throw new ForbiddenException();
            }
        } else {
            // Check update operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.UPDATE)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.UPDATE_OTHER)) {
                throw new ForbiddenException();
            }
        }
    }

    /**
     * Check delete article roles of the logged user.
     *
     * @param entity The entity to save
     */
    public void checkDeleteRoles(final ArticleEntity entity) {
        var loggedUserOwnArticle = StringUtils.equalsIgnoreCase(this.userService.getUserId(), entity.getAuthorId());

        if (entity.getDeletedAt() != null) {
            // Check delete deleted operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.DELETE_DELETED)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.DELETE_OTHER_DELETED)) {
                throw new ForbiddenException();
            }
        } else if (BooleanUtils.isTrue(entity.getIsArchived())) {
            // Check delete archived operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.DELETE_ARCHIVED)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.DELETE_OTHER_ARCHIVED)) {
                throw new ForbiddenException();
            }
        } else {
            // Check delete operations roles

            if (loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.DELETE)) {
                throw new ForbiddenException();
            }

            if (!loggedUserOwnArticle && !this.userService.hasRole(Roles.Article.DELETE_OTHER)) {
                throw new ForbiddenException();
            }
        }
    }

    public void checkSearchRoles(final ArticleSearchCriteria criteria) {
        var searchOwnArticles = StringUtils.equalsIgnoreCase(this.userService.getUserId(), criteria.getAuthorId());

        // Check search operations roles

        if (searchOwnArticles && !this.userService.hasRole(Roles.Article.SEARCH)) {
            throw new ForbiddenException();
        }

        if (!searchOwnArticles && !this.userService.hasRole(Roles.Article.SEARCH_OTHER)) {
            throw new ForbiddenException();
        }

        if (criteria.isDeleted()) {
            // Check search deleted operations roles

            if (searchOwnArticles && !this.userService.hasRole(Roles.Article.SEARCH_DELETED)) {
                throw new ForbiddenException();
            }

            if (!searchOwnArticles && !this.userService.hasRole(Roles.Article.SEARCH_OTHER_DELETED)) {
                throw new ForbiddenException();
            }
        }

        if (criteria.isArchived()) {
            // Check search archived operations roles

            if (searchOwnArticles && !this.userService.hasRole(Roles.Article.SEARCH_ARCHIVED)) {
                throw new ForbiddenException();
            }

            if (!searchOwnArticles && !this.userService.hasRole(Roles.Article.SEARCH_OTHER_ARCHIVED)) {
                throw new ForbiddenException();
            }
        }
    }
}
