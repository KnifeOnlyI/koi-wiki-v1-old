package fr.koi.wikiapi.repository.dao;

import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.repository.ArticleRepository;
import fr.koi.wikiapi.service.article.ArticleAuthenticationService;
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
class ArticleDaoTest extends BaseTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleAuthenticationService articleAuthenticationService;

    @InjectMocks
    private ArticleDao articleDao;

    @Test
    void testGetByIdCallRepositoryAndAuthenticationService() {
        var article = new ArticleEntity().setId(1L);

        when(this.articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        this.articleDao.getById(article.getId());

        verify(this.articleRepository).findById(article.getId());
        verify(this.articleAuthenticationService).checkReadRoles(article);
    }

    @Test
    void testSaveCallRepositoryAndAuthenticationService() {
        var article = new ArticleEntity().setId(1L);

        this.articleDao.save(article);

        verify(this.articleRepository).save(article);
        verify(this.articleAuthenticationService).checkSaveRoles(article);
    }

    @Test
    void testDeleteCallRepositoryAndAuthenticationService() {
        var article = new ArticleEntity().setId(1L);

        when(this.articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        this.articleDao.delete(article.getId());

        verify(this.articleRepository).deleteById(article.getId());
        verify(this.articleAuthenticationService).checkDeleteRoles(article);
    }
}