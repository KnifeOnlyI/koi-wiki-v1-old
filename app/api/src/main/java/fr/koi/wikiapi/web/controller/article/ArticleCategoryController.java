package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.service.article.ArticleCategorySearchService;
import fr.koi.wikiapi.service.article.ArticleCategoryService;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategorySearchCriteria;
import fr.koi.wikiapi.web.model.graphql.article_category.CreateArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.article_category.UpdateArticleCategoryModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to manage article categories.
 */
@RestController
@RequiredArgsConstructor
@Transactional
public class ArticleCategoryController {

    /**
     * The service to manage article categories.
     */
    private final ArticleCategoryService articleCategoryService;

    /**
     * The service to manage article category search.
     */
    private final ArticleCategorySearchService articleCategorySearchService;

    @QueryMapping
    public Page<ArticleCategoryModel> searchArticleCategories(@Argument final ArticleCategorySearchCriteria criteria) {
        return this.articleCategorySearchService.search(criteria);
    }

    @QueryMapping
    public ArticleCategoryModel getArticleCategoryById(@Argument final Long id) {
        return this.articleCategoryService.getById(id);
    }

    @MutationMapping
    public ArticleCategoryModel createArticleCategory(@Argument final CreateArticleCategoryModel data) {
        return this.articleCategoryService.create(data);
    }

    @MutationMapping
    public ArticleCategoryModel updateArticleCategory(@Argument final UpdateArticleCategoryModel data) {
        return this.articleCategoryService.update(data);
    }

    @MutationMapping
    public Long deleteArticleCategory(@Argument final Long id) {
        this.articleCategoryService.delete(id);

        return id;
    }
}
