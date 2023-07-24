package fr.koi.wikiapi.service.article_category;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategorySearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ArticleCategoryAuthenticationServiceTest {
    @InjectMocks
    private ArticleCategoryAuthenticationService testedService;

    @Mock
    private UserService userService;

    @Test
    void testSearchNotDeletedWithSearchRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.SEARCH)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> this.testedService.checkSearchRoles(
            new ArticleCategorySearchCriteria().setDeleted(false))
        );
    }

    @Test
    void testSearchNotDeletedWithoutSearchRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.SEARCH)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(null)
        );
    }

    @Test
    void testSearchDeletedWithSearchAndReadDeletedRolesNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.SEARCH)).thenReturn(true);
        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> this.testedService.checkSearchRoles(
            new ArticleCategorySearchCriteria().setDeleted(true)
        ));
    }

    @Test
    void testSearchDeletedWithSearchRoleAndWithoutReadDeletedRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.SEARCH)).thenReturn(true);
        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSearchRoles(
                new ArticleCategorySearchCriteria().setDeleted(true)
            )
        );
    }

    @Test
    void testReadWithReadRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.READ)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkReadRoles(
                new ArticleCategoryEntity()
            )
        );
    }

    @Test
    void testReadWithoutReadRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.READ)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(
                new ArticleCategoryEntity()
            )
        );
    }

    @Test
    void testReadDeletedWithReadDeletedRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkReadRoles(
                new ArticleCategoryEntity().setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testReadDeletedWithoutReadDeletedRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkReadRoles(
                new ArticleCategoryEntity().setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testDeleteWithDeleteRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(
                new ArticleCategoryEntity()
            )
        );
    }

    @Test
    void testDeleteWithoutDeleteRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(
                new ArticleCategoryEntity()
            )
        );
    }

    @Test
    void testDeleteDeletedWithDeleteDeletedRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE_DELETED)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkDeleteRoles(
                new ArticleCategoryEntity().setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testDeleteDeletedWithoutDeleteDeletedRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE_DELETED)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkDeleteRoles(
                new ArticleCategoryEntity().setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testCreateWithoutCreateRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.CREATE)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(
                new ArticleCategoryEntity()
            )
        );
    }

    @Test
    void testCreateWithCreateRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.CREATE)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(
                new ArticleCategoryEntity()
            )
        );
    }

    @Test
    void testUpdateWithUpdateRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.UPDATE)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(
                new ArticleCategoryEntity().setId(1L)
            )
        );
    }

    @Test
    void testUpdateWithoutUpdateRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.UPDATE)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(
                new ArticleCategoryEntity().setId(1L)
            )
        );
    }

    @Test
    void testUpdateDeletedWithUpdateDeletedRoleNotThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.UPDATE_DELETED)).thenReturn(true);

        Assertions.assertDoesNotThrow(
            () -> this.testedService.checkSaveRoles(
                new ArticleCategoryEntity().setId(1L).setDeletedAt(ZonedDateTime.now())
            )
        );
    }

    @Test
    void testUpdatedeletedWithoutUpdatedeletedRoleThrowsException() {
        when(this.userService.hasRole(Roles.ArticleCategory.UPDATE_DELETED)).thenReturn(false);

        Assertions.assertThrows(
            ForbiddenException.class,
            () -> this.testedService.checkSaveRoles(
                new ArticleCategoryEntity().setId(1L).setDeletedAt(ZonedDateTime.now())
            )
        );
    }
}