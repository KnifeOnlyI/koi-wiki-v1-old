package fr.koi.wikiapi.mapper;

import fr.koi.wikiapi.constants.MapStructs;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.domain.ArticleEntity;
import fr.koi.wikiapi.mapper.utils.StringMapper;
import fr.koi.wikiapi.repository.dao.ArticleCategoryDao;
import fr.koi.wikiapi.web.model.article.ArticleModel;
import fr.koi.wikiapi.web.model.article.CreateOrUpdateArticleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The mapper for article.
 */
@Mapper(componentModel = "spring", uses = {
    StringMapper.class
})
public abstract class ArticleMapper {

    /**
     * The DAO to manage article categories in the database.
     */
    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    /**
     * Map the specified entity to ID.
     *
     * @param entity The entity to map
     *
     * @return The corresponding ID
     */
    public Long toId(ArticleCategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        return entity.getId();
    }

    /**
     * Map the specified entities to ID list.
     *
     * @param entities The entities to map
     *
     * @return The correspondin ID list
     */
    public abstract List<Long> toIds(List<ArticleCategoryEntity> entities);

    /**
     * Map the specified ID list to entities.
     *
     * @param ids The ID list to map
     *
     * @return The corresponding entities
     */
    public List<ArticleCategoryEntity> toArticleCategoryEntities(List<Long> ids) {
        if (ids == null) {
            return new ArrayList<>();
        }

        return ids
            .stream()
            .map(id -> this.articleCategoryDao.getById(id))
            .collect(Collectors.toList());
    }

    /**
     * Map the specified entity to model.
     *
     * @param entity The entity to map
     *
     * @return The corresponding model
     */
    public abstract ArticleModel toModel(ArticleEntity entity);

    /**
     * Map the specified model to entity.
     *
     * @param model The model to map
     *
     * @return The corresponding entity
     */
    @Mapping(target = "createdAt", expression = MapStructs.Expressions.ZONED_DATE_TIME_NOW)
    @Mapping(target = "description", source = "description", qualifiedBy = StringMapper.NullableStringToNotNullString.class)
    @Mapping(target = "content", source = "content", qualifiedBy = StringMapper.NullableStringToNotNullString.class)
    public abstract ArticleEntity toEntity(CreateOrUpdateArticleModel model);

    /**
     * Map the specified entities to models.
     *
     * @param entities The entities to map
     *
     * @return The corresponding models
     */
    public abstract List<ArticleModel> toModels(List<ArticleEntity> entities);

    /**
     * Update the specified entity with the value of the specified model.
     *
     * @param entity The entity to udpate
     * @param model  The model which contains the data
     */
    @Mapping(target = "lastUpdateAt", expression = MapStructs.Expressions.ZONED_DATE_TIME_NOW)
    @Mapping(target = "description", source = "description", qualifiedBy = StringMapper.NullableStringToNotNullString.class)
    @Mapping(target = "content", source = "content", qualifiedBy = StringMapper.NullableStringToNotNullString.class)
    public abstract void updateEntity(@MappingTarget ArticleEntity entity, CreateOrUpdateArticleModel model);
}
