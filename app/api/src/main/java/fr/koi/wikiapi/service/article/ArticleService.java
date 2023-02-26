package fr.koi.wikiapi.service.article;

import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.mapper.ArticleMapper;
import fr.koi.wikiapi.repository.dao.ArticleDao;
import fr.koi.wikiapi.service.user.UserService;
import fr.koi.wikiapi.web.model.article.ArticleModel;
import fr.koi.wikiapi.web.model.article.CreateOrUpdateArticleModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The service to manage article.
 */
@Service
@RequiredArgsConstructor
public class ArticleService {
    /**
     * The DAO to manage article in the database.
     */
    private final ArticleDao articleDao;

    /**
     * The mapper for article.
     */
    private final ArticleMapper articleMapper;

    /**
     * The service to manage users.
     */
    private final UserService userService;

    /**
     * Get a model with the specified ID and throw an error if not exists.
     *
     * @param id The ID
     *
     * @return The found model
     */
    public ArticleModel getById(final Long id) {
        return this.articleMapper.toModel(this.articleDao.getById(id));
    }

    /**
     * Create a new entity.
     *
     * @param data The data of entity to create
     *
     * @return A model that represent the created entity
     */
    public ArticleModel create(final CreateOrUpdateArticleModel data) {
        ArticleEntity entity = this.articleMapper.toEntity(data)
            .setAuthorId(this.userService.getUserId());

        this.articleDao.save(entity);

        return this.articleMapper.toModel(entity);
    }

    /**
     * Update an entity based on the specified data.
     *
     * @param id   The ID of entity to update
     * @param data The data of entity to create
     *
     * @return A model that represent the updated entity
     */
    public ArticleModel update(final Long id, final CreateOrUpdateArticleModel data) {
        ArticleEntity entity = this.articleDao.getById(id);

        this.articleMapper.updateEntity(entity, data);

        return this.articleMapper.toModel(entity);
    }

    /**
     * Delete the specified entity identified by his ID.
     *
     * @param id The ID of entity to delete
     */
    public void delete(final Long id) {
        this.articleDao.delete(id);
    }
}
