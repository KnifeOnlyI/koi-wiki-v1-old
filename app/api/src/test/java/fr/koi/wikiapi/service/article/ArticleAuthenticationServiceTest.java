package fr.koi.wikiapi.service.article;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article.ArticleSearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ArticleAuthenticationServiceTest {
    @InjectMocks
    private ArticleAuthenticationService testedService;

    @Mock
    private UserService userService;

    @Test
    void testReadOwnedWithoutReadRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.READ)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(new ArticleEntity().setAuthorId(authorId))
        );
    }

    @Test
    void testReadOwnedWithReadRoleNotThrowsException() {
        var userId = "1";

        when(this.userService.hasRole(Roles.Article.READ)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(() -> this.testedService.checkReadRoles(new ArticleEntity().setAuthorId(userId)));
    }

    @Test
    void testReadNotOwnedWithoutReadOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.READ_OTHER)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testReadNotOwnedWithReadOtherRoleNotThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.READ_OTHER)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testReadDeletedOwnedWithReadDeletedRoleNotThrowsException() {
        var userId = "1";

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(() -> this.testedService.checkReadRoles(new ArticleEntity()
            .setAuthorId(userId)
            .setDeletedAt(ZonedDateTime.now())
        ));
    }

    @Test
    void testReadDeletedOwnedWithoutReadDeletedRoleThrowsException() {
        var userId = "1";

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(userId)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testReadArchivedOwnedWithReadArchivedRoleNotThrowsException() {
        var userId = "1";

        when(this.userService.hasRole(Roles.Article.READ_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(() -> this.testedService.checkReadRoles(new ArticleEntity()
            .setAuthorId(userId)
            .setIsArchived(true)
        ));
    }

    @Test
    void testReadArchivedOwnedWithoutReadArchivedRoleThrowsException() {
        var userId = "1";

        when(this.userService.hasRole(Roles.Article.READ_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(userId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testReadDeletedNotOwnedWithReadDeletedRoleNotThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.READ_OTHER_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(() -> this.testedService.checkReadRoles(new ArticleEntity()
            .setAuthorId(authorId)
            .setDeletedAt(ZonedDateTime.now())
        ));
    }

    @Test
    void testReadDeletedNotOwnedWithoutReadDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.READ_OTHER_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(authorId)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testReadArchivedNotOwnedWithoutReadArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.READ_OTHER_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testReadArchivedNotOwnedWithReadArchivedRoleNotThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.READ_OTHER_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkReadRoles(new ArticleEntity()
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testCreateArticleWithoutCreateRoleThrowsException() {
        when(this.userService.hasRole(Roles.Article.CREATE)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity())
        );
    }

    @Test
    void testCreateArticleWithCreateRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.Article.CREATE)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity())
        );
    }

    @Test
    void testUpdateOwnedArticleWithoutUpdateRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.UPDATE)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testUpdateOwnedArticleWithUpdateRoleNotThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.UPDATE)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testUpdateNotOwnedArticleWithoutUpdateOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.UPDATE_OTHER)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testUpdateNotOwnedArticleWithUpdateOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.UPDATE_OTHER)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testUpdateOwnedArchivedArticleWithoutUpdateArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.UPDATE_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testUpdateOwnedArchivedArticleWithUpdateArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.UPDATE_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testUpdateNotOwnedArchivedArticleWithoutUpdateNotOwnedArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.UPDATE_OTHER_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testUpdateNotOwnedArchivedArticleWithUpdateNotOwnedArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.UPDATE_OTHER_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testUpdateOwnedDeletedArticleWithoutUpdateOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.UPDATE_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testUpdateOwnedDeletedArticleWithUpdateOwnedDeletedRoleNotThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.UPDATE_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testUpdateNotOwnedDeletedArticleWithoutUpdateNotOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.UPDATE_OTHER_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testUpdateNotOwnedDeletedArticleWithUpdateNotOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.UPDATE_OTHER_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testDeleteOwnedArticleWithoutDeleteRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.DELETE)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testDeleteOwnedArticleWithDeleteRoleNotThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.DELETE)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testDeleteNotOwnedArticleWithoutDeleteOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.DELETE_OTHER)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testDeleteNotOwnedArticleWithDeleteOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.DELETE_OTHER)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testDeleteOwnedArchivedArticleWithoutDeleteArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.DELETE_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testDeleteOwnedArchivedArticleWithDeleteArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.DELETE_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testDeleteNotOwnedArchivedArticleWithoutDeleteNotOwnedArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.DELETE_OTHER_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testDeleteNotOwnedArchivedArticleWithDeleteNotOwnedArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.DELETE_OTHER_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
            )
        );
    }

    @Test
    void testDeleteOwnedDeletedArticleWithoutDeleteOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.DELETE_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testDeleteOwnedDeletedArticleWithDeleteOwnedDeletedRoleNotThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.DELETE_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testDeleteNotOwnedDeletedArticleWithoutDeleteNotOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.DELETE_OTHER_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testDeleteNotOwnedDeletedArticleWithDeleteNotOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.DELETE_OTHER_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(new ArticleEntity()
                .setId(1L)
                .setAuthorId(authorId)
                .setIsArchived(true)
                .setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testSearchOwnedArticleWithoutSearchRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.SEARCH)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testSearchOwnedArticleWithSearchRoleNotThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.SEARCH)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testSearchNotOwnedArticleWithoutSearchOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testSearchNotOwnedArticleWithSearchOtherRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
            )
        );
    }

    @Test
    void testSearchOwnedArchivedArticleWithoutSearchArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.SEARCH)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setArchived(true)
            )
        );
    }

    @Test
    void testSearchOwnedArchivedArticleWithSearchArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.SEARCH)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setArchived(true)
            )
        );
    }

    @Test
    void testSearchNotOwnedArchivedArticleWithoutSearchNotOwnedArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER_ARCHIVED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setArchived(true)
            )
        );
    }

    @Test
    void testSearchNotOwnedArchivedArticleWithSearchNotOwnedArchivedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";


        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER_ARCHIVED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setArchived(true)
            )
        );
    }

    @Test
    void testSearchOwnedDeletedArticleWithoutSearchOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.SEARCH)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setDeleted(true)
            )
        );
    }

    @Test
    void testSearchOwnedDeletedArticleWithSearchOwnedDeletedRoleNotThrowsException() {
        var userId = "1";
        var authorId = "1";

        when(this.userService.hasRole(Roles.Article.SEARCH)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setDeleted(true)
            )
        );
    }

    @Test
    void testSearchNotOwnedDeletedArticleWithoutSearchNotOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER_DELETED)).thenReturn(false);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setDeleted(true)
            )
        );
    }

    @Test
    void testSearchNotOwnedDeletedArticleWithSearchNotOwnedDeletedRoleThrowsException() {
        var userId = "1";
        var authorId = "2";

        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.SEARCH_OTHER_DELETED)).thenReturn(true);
        when(this.userService.getUserId()).thenReturn(userId);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSearchRoles(new ArticleSearchCriteria()
                .setAuthorId(authorId)
                .setDeleted(true)
            )
        );
    }
}