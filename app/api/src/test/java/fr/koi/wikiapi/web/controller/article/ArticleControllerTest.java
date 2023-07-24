package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.service.article.ArticleSearchService;
import fr.koi.wikiapi.service.article.ArticleService;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article.ArticleSearchCriteria;
import fr.koi.wikiapi.web.model.graphql.article.CreateArticleModel;
import fr.koi.wikiapi.web.model.graphql.article.UpdateArticleModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ArticleControllerTest {
    @Mock
    private ArticleService articleService;

    @Mock
    private ArticleSearchService articleSearchService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ArticleController articleController;

    @Test
    void testSearchArticlesCheckLoggedUserAndCallService() {
        var criteria = new ArticleSearchCriteria();

        this.articleController.searchArticles(criteria);

        verify(this.userService).assertUserLogged();
        verify(this.articleSearchService).search(criteria);
    }

    @Test
    void testGetArticleByIdCheckLoggedUserAndCallService() {
        var articleId = 1L;

        this.articleController.getArticleById(articleId);

        verify(this.userService).assertUserLogged();
        verify(this.articleService).getById(articleId);
    }

    @Test
    void testCreateArticleCheckLoggedUserAndCallService() {
        var article = new CreateArticleModel();

        this.articleController.createArticle(article);

        verify(this.userService).assertUserLogged();
        verify(this.articleService).create(article);
    }

    @Test
    void testUpdateArticleCheckLoggedUserAndCallService() {
        var article = new UpdateArticleModel();

        this.articleController.updateArticle(article);

        verify(this.userService).assertUserLogged();
        verify(this.articleService).update(article);
    }

    @Test
    void testDeleteArticleByIdCheckLoggedUserAndCallService() {
        var articleId = 1L;

        this.articleController.deleteArticleById(articleId);

        verify(this.userService).assertUserLogged();
        verify(this.articleService).delete(articleId);
    }
}