package fr.koi.wikiapi.web.controller.article;

import fr.koi.wikiapi.service.article.ArticleSearchService;
import fr.koi.wikiapi.service.article.ArticleService;
import fr.koi.wikiapi.web.model.graphql.article.ArticleModel;
import fr.koi.wikiapi.web.model.graphql.article.ArticleSearchCriteria;
import fr.koi.wikiapi.web.model.graphql.article.CreateArticleModel;
import fr.koi.wikiapi.web.model.graphql.article.UpdateArticleModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller to manage articles.
 */
@RestController
@RequiredArgsConstructor
@Transactional
public class ArticleController {

    /**
     * The service to manage articles.
     */
    private final ArticleService articleService;

    /**
     * The service to manage article search.
     */
    private final ArticleSearchService articleSearchService;

    /**
     * Search entities.
     *
     * @param criteria The criteria
     *
     * @return The results
     */
    @QueryMapping
    public Page<ArticleModel> searchArticles(@Argument final ArticleSearchCriteria criteria) {
        return this.articleSearchService.search(criteria);
    }

    /**
     * Get a model with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found model
     */
    @QueryMapping
    public ArticleModel getArticleById(@Argument final Long id) {
        return this.articleService.getById(id);
    }

    /**
     * Create a new entity.
     *
     * @param data The data of entity to create
     *
     * @return The created entity
     */
    @MutationMapping
    public ArticleModel createArticle(@Argument final CreateArticleModel data) {
        return this.articleService.create(data);
    }

    /**
     * Update an entity based on the specified data.
     *
     * @param data The data of entity to create
     *
     * @return A model that represent the updated entity
     */
    @MutationMapping
    public ArticleModel updateArticle(@Argument final UpdateArticleModel data) {
        return this.articleService.update(data);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     *
     * @return The ID of deleted entity
     */
    @MutationMapping
    public Long deleteArticle(@Argument final Long id) {
        this.articleService.delete(id);

        return id;
    }
}
