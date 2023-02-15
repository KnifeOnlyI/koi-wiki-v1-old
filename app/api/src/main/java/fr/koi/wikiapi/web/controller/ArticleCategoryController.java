package fr.koi.wikiapi.web.controller;

import fr.koi.wikiapi.constants.Urls;
import fr.koi.wikiapi.service.article.ArticleCategorySearchService;
import fr.koi.wikiapi.service.article.ArticleCategoryService;
import fr.koi.wikiapi.web.model.article.ArticleCategoryModel;
import fr.koi.wikiapi.web.model.article.ArticleCategorySearchCriteria;
import fr.koi.wikiapi.web.model.article.CreateOrUpdateArticleCategoryModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
     * Search entities.
     *
     * @param criteria The criteria
     *
     * @return The results
     */
    @GetMapping(Urls.ArticleCategory.BASE)
    public Page<ArticleCategoryModel> search(final ArticleCategorySearchCriteria criteria) {
        return this.articleCategorySearchService.search(criteria);
    }

    /**
     * Get a model with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found model
     */
    @GetMapping(Urls.ArticleCategory.UNIQUE)
    public ArticleCategoryModel getById(@PathVariable final Long id) {
        return this.articleCategoryService.getById(id);
    }

    /**
     * Create a new entity.
     *
     * @param data The data of entity to create
     *
     * @return The created entity
     */
    @PostMapping(Urls.ArticleCategory.BASE)
    public ArticleCategoryModel create(@RequestBody @Valid final CreateOrUpdateArticleCategoryModel data) {
        return this.articleCategoryService.create(data);
    }

    /**
     * Update an entity based on the specified data.
     *
     * @param id   The ID of entity to update
     * @param data The data of entity to create
     *
     * @return A model that represent the updated entity
     */
    @PutMapping(Urls.ArticleCategory.UNIQUE)
    public ArticleCategoryModel update(
        @PathVariable final Long id,
        @RequestBody @Valid final CreateOrUpdateArticleCategoryModel data
    ) {
        return this.articleCategoryService.update(id, data);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    @DeleteMapping(Urls.ArticleCategory.UNIQUE)
    public void delete(@PathVariable final Long id) {
        this.articleCategoryService.delete(id);
    }
}
