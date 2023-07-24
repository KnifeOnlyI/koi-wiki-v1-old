package fr.koi.wikiapi.web.controller.article_category;

import fr.koi.wikiapi.service.article_category.ArticleCategorySearchService;
import fr.koi.wikiapi.service.article_category.ArticleCategoryService;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategorySearchCriteria;
import fr.koi.wikiapi.web.model.graphql.article_category.CreateArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.article_category.UpdateArticleCategoryModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ArticleCategoryControllerTest {
    @Mock
    private ArticleCategoryService articleCategoryService;

    @Mock
    private ArticleCategorySearchService articleCategorySearchService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ArticleCategoryController articleCategoryController;

    @Test
    void testSearchArticleCategorysCheckLoggedUserAndCallService() {
        var criteria = new ArticleCategorySearchCriteria();

        this.articleCategoryController.searchArticleCategories(criteria);

        verify(this.userService).assertUserLogged();
        verify(this.articleCategorySearchService).search(criteria);
    }

    @Test
    void testGetArticleCategoryByIdCheckLoggedUserAndCallService() {
        var articleCategoryId = 1L;

        this.articleCategoryController.getArticleCategoryById(articleCategoryId);

        verify(this.userService).assertUserLogged();
        verify(this.articleCategoryService).getById(articleCategoryId);
    }

    @Test
    void testCreateArticleCategoryCheckLoggedUserAndCallService() {
        var articleCategory = new CreateArticleCategoryModel();

        this.articleCategoryController.createArticleCategory(articleCategory);

        verify(this.userService).assertUserLogged();
        verify(this.articleCategoryService).create(articleCategory);
    }

    @Test
    void testUpdateArticleCategoryCheckLoggedUserAndCallService() {
        var articleCategory = new UpdateArticleCategoryModel();

        this.articleCategoryController.updateArticleCategory(articleCategory);

        verify(this.userService).assertUserLogged();
        verify(this.articleCategoryService).update(articleCategory);
    }

    @Test
    void testDeleteArticleCategoryByIdCheckLoggedUserAndCallService() {
        var articleCategoryId = 1L;

        this.articleCategoryController.deleteArticleCategoryById(articleCategoryId);

        verify(this.userService).assertUserLogged();
        verify(this.articleCategoryService).delete(articleCategoryId);
    }
}