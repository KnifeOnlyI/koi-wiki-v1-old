package fr.koi.wikiapi.service.article;

import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.mapper.ArticleCategoryMapper;
import fr.koi.wikiapi.repository.dao.ArticleCategoryDao;
import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.article_category.CreateArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.article_category.UpdateArticleCategoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The service to manage article categories.
 */
@Service
@RequiredArgsConstructor
public class ArticleCategoryService {
    /**
     * The DAO to manage article categories in the database.
     */
    private final ArticleCategoryDao articleCategoryDao;

    /**
     * The mapper for article categories.
     */
    private final ArticleCategoryMapper articleCategoryMapper;

    /**
     * Get a model with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found model
     */
    public ArticleCategoryModel getById(final Long id) {
        return this.articleCategoryMapper.toModel(this.articleCategoryDao.getById(id));
    }

    /**
     * Create a new entity.
     *
     * @param data The data of entity to create
     *
     * @return A model that represent the created entity
     */
    public ArticleCategoryModel create(final CreateArticleCategoryModel data) {
        ArticleCategoryEntity entity = this.articleCategoryMapper.toEntity(data);

        this.articleCategoryDao.save(entity);

        return this.articleCategoryMapper.toModel(entity);
    }

    /**
     * Update an entity based on the specified data.
     *
     * @param data The data of entity to create
     *
     * @return A model that represent the updated entity
     */
    public ArticleCategoryModel update(final UpdateArticleCategoryModel data) {
        ArticleCategoryEntity entity = this.articleCategoryDao.getById(data.getId());

        this.articleCategoryMapper.updateEntity(entity, data);

        return this.articleCategoryMapper.toModel(entity);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    public void delete(final Long id) {
        this.articleCategoryDao.delete(id);
    }
}
