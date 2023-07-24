package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.repository.ArticleCategoryRepository;
import fr.koi.wikiapi.service.article_category.ArticleCategoryAuthenticationService;
import fr.koi.wikiapi.web.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ArticleCategoryDaoTest extends BaseTest {

    @Mock
    private ArticleCategoryRepository articleCategoryRepository;

    @Mock
    private ArticleCategoryAuthenticationService articleCategoryAuthenticationService;

    @InjectMocks
    private ArticleCategoryDao articleCategoryDao;

    @Test
    void testGetByIdCallRepositoryAndAuthenticationService() {
        var articleCategory = new ArticleCategoryEntity().setId(1L);

        when(this.articleCategoryRepository.findById(articleCategory.getId())).thenReturn(Optional.of(articleCategory));

        this.articleCategoryDao.getById(articleCategory.getId());

        verify(this.articleCategoryRepository).findById(articleCategory.getId());
        verify(this.articleCategoryAuthenticationService).checkReadRoles(articleCategory);
    }

    @Test
    void testSaveCallRepositoryAndAuthenticationService() {
        var articleCategory = new ArticleCategoryEntity().setId(1L);

        this.articleCategoryDao.save(articleCategory);

        verify(this.articleCategoryRepository).save(articleCategory);
        verify(this.articleCategoryAuthenticationService).checkSaveRoles(articleCategory);
    }

    @Test
    void testDeleteCallRepositoryAndAuthenticationService() {
        var articleCategory = new ArticleCategoryEntity().setId(1L);

        when(this.articleCategoryRepository.findById(articleCategory.getId())).thenReturn(Optional.of(articleCategory));

        this.articleCategoryDao.delete(articleCategory.getId());

        verify(this.articleCategoryRepository).deleteById(articleCategory.getId());
        verify(this.articleCategoryAuthenticationService).checkDeleteRoles(articleCategory);
    }
}