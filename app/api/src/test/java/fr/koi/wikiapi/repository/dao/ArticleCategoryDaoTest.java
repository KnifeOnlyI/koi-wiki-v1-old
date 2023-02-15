package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.dto.exception.NotFoundResourceException;
import fr.koi.wikiapi.dto.exception.article.NotFoundArticleCategoryException;
import fr.koi.wikiapi.repository.ArticleCategoryRepository;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ArticleCategoryDaoTest extends BaseTest {
    private ArticleCategoryDao articleCategoryDao;

    @Mock
    private ArticleCategoryRepository articleCategoryRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.articleCategoryDao = new ArticleCategoryDao(this.articleCategoryRepository, this.userService);
    }

    @Test
    void testGetByIdWithoutReadRole() {
        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.ArticleCategory.READ)).thenReturn(false);

        assertThatThrownBy(() -> this.articleCategoryDao.getById(1L))
            .isOfAnyClassIn(NotFoundArticleCategoryException.class);
    }

    @Test
    void testGetByIdWithReadRole() {
        ArticleCategoryEntity mockData = new ArticleCategoryEntity().setId(1L);

        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.ArticleCategory.READ)).thenReturn(true);
        when(this.articleCategoryRepository.findByIdAndDeletedAtIsNull(mockData.getId()))
            .thenReturn(Optional.of(mockData));

        assertThat(this.articleCategoryDao.getById(mockData.getId())).isNotNull();

        verify(this.articleCategoryRepository).findByIdAndDeletedAtIsNull(mockData.getId());
        verify(this.articleCategoryRepository, never()).findById(any());
    }

    @Test
    void testGetByIdWithReadDeletedRole() {
        ArticleCategoryEntity mockData = new ArticleCategoryEntity().setId(1L);

        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(true);
        when(this.articleCategoryRepository.findById(mockData.getId())).thenReturn(Optional.of(mockData));

        assertThat(this.articleCategoryDao.getById(mockData.getId())).isNotNull();

        verify(this.articleCategoryRepository, never()).findByIdAndDeletedAtIsNull(any());
        verify(this.articleCategoryRepository).findById(mockData.getId());
    }

    @Test
    void testCreateWithoutRole() {
        assertThatThrownBy(() -> this.articleCategoryDao.save(new ArticleCategoryEntity()))
            .isOfAnyClassIn(NotFoundResourceException.class);
    }

    @Test
    void testUpdateNotDeletedWithoutRole() {
        assertThatThrownBy(() -> this.articleCategoryDao.save(new ArticleCategoryEntity().setId(1L)))
            .isOfAnyClassIn(NotFoundResourceException.class);
    }

    @Test
    void testUpdateDeletedWithoutRole() {
        assertThatThrownBy(() -> this.articleCategoryDao.save(new ArticleCategoryEntity()
            .setId(1L)
            .setDeletedAt(this.getNowZonedDateTime())
        )).isOfAnyClassIn(NotFoundResourceException.class);
    }

    @Test
    void testDeleteWithRole() {
        ArticleCategoryEntity mockData = new ArticleCategoryEntity().setId(1L);

        when(this.userService.hasRole(Roles.ArticleCategory.READ)).thenReturn(true);
        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE)).thenReturn(true);
        when(this.articleCategoryRepository.findByIdAndDeletedAtIsNull(mockData.getId()))
            .thenReturn(Optional.of(mockData));

        this.articleCategoryDao.delete(mockData.getId());

        assertThat(mockData.getDeletedAt()).isNotNull();

        verify(this.articleCategoryRepository, never()).deleteById(mockData.getId());
    }

    @Test
    void testDeleteDeletedWithRole() {
        ArticleCategoryEntity mockData = new ArticleCategoryEntity().setId(1L).setDeletedAt(this.getNowZonedDateTime());

        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(true);
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE_DELETED)).thenReturn(true);
        when(this.articleCategoryRepository.findById(mockData.getId())).thenReturn(Optional.of(mockData));

        this.articleCategoryDao.delete(mockData.getId());

        verify(this.articleCategoryRepository).deleteById(mockData.getId());
    }

    @Test
    void testDeleteWithoutRole() {
        ArticleCategoryEntity mockData = new ArticleCategoryEntity().setId(1L);

        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.ArticleCategory.READ)).thenReturn(true);
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE)).thenReturn(false);
        when(this.articleCategoryRepository.findByIdAndDeletedAtIsNull(mockData.getId()))
            .thenReturn(Optional.of(mockData));

        assertThatThrownBy(() -> this.articleCategoryDao.delete(mockData.getId()))
            .isOfAnyClassIn(NotFoundArticleCategoryException.class);
    }

    @Test
    void testDeleteDeletedWithoutRole() {
        ArticleCategoryEntity mockData = new ArticleCategoryEntity().setId(1L).setDeletedAt(this.getNowZonedDateTime());

        when(this.userService.hasRole(Roles.ArticleCategory.READ_DELETED)).thenReturn(true);
        when(this.userService.hasRole(Roles.ArticleCategory.DELETE_DELETED)).thenReturn(false);
        when(this.articleCategoryRepository.findById(mockData.getId())).thenReturn(Optional.of(mockData));

        assertThatThrownBy(() -> this.articleCategoryDao.delete(mockData.getId()))
            .isOfAnyClassIn(NotFoundArticleCategoryException.class);
    }
}