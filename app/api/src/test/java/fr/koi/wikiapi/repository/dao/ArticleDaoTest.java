package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.constants.Roles;
import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.dto.exception.NotFoundResourceException;
import fr.koi.wikiapi.dto.exception.article.NotFoundArticleException;
import fr.koi.wikiapi.repository.ArticleRepository;
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
class ArticleDaoTest extends BaseTest {
    private ArticleDao articleDao;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.articleDao = new ArticleDao(this.articleRepository, this.userService);
    }

    @Test
    void testGetByIdWithoutReadRole() {
        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.Article.READ)).thenReturn(false);

        assertThatThrownBy(() -> this.articleDao.getById(1L))
            .isOfAnyClassIn(NotFoundArticleException.class);
    }

    @Test
    void testGetByIdWithReadRole() {
        ArticleEntity mockData = new ArticleEntity().setId(1L);

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.Article.READ)).thenReturn(true);
        when(this.articleRepository.findByIdAndDeletedAtIsNull(mockData.getId()))
            .thenReturn(Optional.of(mockData));

        assertThat(this.articleDao.getById(mockData.getId())).isNotNull();

        verify(this.articleRepository).findByIdAndDeletedAtIsNull(mockData.getId());
        verify(this.articleRepository, never()).findById(any());
    }

    @Test
    void testGetByIdWithReadDeletedRole() {
        ArticleEntity mockData = new ArticleEntity().setId(1L);

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(true);
        when(this.articleRepository.findById(mockData.getId())).thenReturn(Optional.of(mockData));

        assertThat(this.articleDao.getById(mockData.getId())).isNotNull();

        verify(this.articleRepository, never()).findByIdAndDeletedAtIsNull(any());
        verify(this.articleRepository).findById(mockData.getId());
    }

    @Test
    void testCreateWithoutRole() {
        assertThatThrownBy(() -> this.articleDao.save(new ArticleEntity()))
            .isOfAnyClassIn(NotFoundResourceException.class);
    }

    @Test
    void testUpdateNotDeletedWithoutRole() {
        assertThatThrownBy(() -> this.articleDao.save(new ArticleEntity().setId(1L)))
            .isOfAnyClassIn(NotFoundResourceException.class);
    }

    @Test
    void testUpdateDeletedWithoutRole() {
        assertThatThrownBy(() -> this.articleDao.save(new ArticleEntity()
            .setId(1L)
            .setDeletedAt(this.getNowZonedDateTime())
        )).isOfAnyClassIn(NotFoundResourceException.class);
    }

    @Test
    void testDeleteWithRole() {
        ArticleEntity mockData = new ArticleEntity().setId(1L);

        when(this.userService.hasRole(Roles.Article.READ)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.Article.DELETE)).thenReturn(true);
        when(this.articleRepository.findByIdAndDeletedAtIsNull(mockData.getId()))
            .thenReturn(Optional.of(mockData));

        this.articleDao.delete(mockData.getId());

        assertThat(mockData.getDeletedAt()).isNotNull();

        verify(this.articleRepository, never()).deleteById(mockData.getId());
    }

    @Test
    void testDeleteDeletedWithRole() {
        ArticleEntity mockData = new ArticleEntity().setId(1L).setDeletedAt(this.getNowZonedDateTime());

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.DELETE_DELETED)).thenReturn(true);
        when(this.articleRepository.findById(mockData.getId())).thenReturn(Optional.of(mockData));

        this.articleDao.delete(mockData.getId());

        verify(this.articleRepository).deleteById(mockData.getId());
    }

    @Test
    void testDeleteWithoutRole() {
        ArticleEntity mockData = new ArticleEntity().setId(1L);

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(false);
        when(this.userService.hasRole(Roles.Article.READ)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.DELETE)).thenReturn(false);
        when(this.articleRepository.findByIdAndDeletedAtIsNull(mockData.getId()))
            .thenReturn(Optional.of(mockData));

        assertThatThrownBy(() -> this.articleDao.delete(mockData.getId()))
            .isOfAnyClassIn(NotFoundArticleException.class);
    }

    @Test
    void testDeleteDeletedWithoutRole() {
        ArticleEntity mockData = new ArticleEntity().setId(1L).setDeletedAt(this.getNowZonedDateTime());

        when(this.userService.hasRole(Roles.Article.READ_DELETED)).thenReturn(true);
        when(this.userService.hasRole(Roles.Article.DELETE_DELETED)).thenReturn(false);
        when(this.articleRepository.findById(mockData.getId())).thenReturn(Optional.of(mockData));

        assertThatThrownBy(() -> this.articleDao.delete(mockData.getId()))
            .isOfAnyClassIn(NotFoundArticleException.class);
    }
}