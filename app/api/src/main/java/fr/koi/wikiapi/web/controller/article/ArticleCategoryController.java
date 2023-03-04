package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.service.article.ArticleCategorySearchService;
import fr.koi.wikiapi.service.article.ArticleCategoryService;
import fr.koi.wikiapi.service.user.UserService;
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

    /**
     * The service to manage users.
     */
    private final UserService userService;

    /**
     * Search entities.
     *
     * @param criteria The criteria
     *
     * @return The results
     */
    @QueryMapping
    public Page<ArticleCategoryModel> searchArticleCategories(@Argument final ArticleCategorySearchCriteria criteria) {
        this.userService.assertUserLogged();

        return this.articleCategorySearchService.search(criteria);
    }

    /**
     * Get a model with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found model
     */
    @QueryMapping
    public ArticleCategoryModel getArticleCategoryById(@Argument final Long id) {
        this.userService.assertUserLogged();

        return this.articleCategoryService.getById(id);
    }

    /**
     * Create a new entity.
     *
     * @param data The data of entity to create
     *
     * @return The created entity
     */
    @MutationMapping
    public ArticleCategoryModel createArticleCategory(@Argument final CreateArticleCategoryModel data) {
        this.userService.assertUserLogged();

        return this.articleCategoryService.create(data);
    }

    /**
     * Update an entity based on the specified data.
     *
     * @param data The data of entity to create
     *
     * @return A model that represent the updated entity
     */
    @MutationMapping
    public ArticleCategoryModel updateArticleCategory(@Argument final UpdateArticleCategoryModel data) {
        this.userService.assertUserLogged();

        return this.articleCategoryService.update(data);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     *
     * @return The ID of deleted entity
     */
    @MutationMapping
    public Long deleteArticleCategory(@Argument final Long id) {
        this.userService.assertUserLogged();

        this.articleCategoryService.delete(id);

        return id;
    }
}
