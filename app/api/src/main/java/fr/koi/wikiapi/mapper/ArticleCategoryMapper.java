package fr.koi.wikiapi.mapper;

import fr.koi.wikiapi.constants.MapStructs;
import fr.koi.wikiapi.domain.ArticleCategoryEntity;
import fr.koi.wikiapi.mapper.utils.StringMapper;
import fr.koi.wikiapi.web.model.article.ArticleCategoryModel;
import fr.koi.wikiapi.web.model.article.CreateOrUpdateArticleCategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * The mapper for article categories.
 */
@Mapper(componentModel = "spring", uses = {
    StringMapper.class
})
public interface ArticleCategoryMapper {
    /**
     * Map the specified entity to model.
     *
     * @param entity The entity to map
     *
     * @return The corresponding model
     */
    ArticleCategoryModel toModel(ArticleCategoryEntity entity);

    /**
     * Map the specified model to entity.
     *
     * @param model The model to map
     *
     * @return The corresponding entity
     */
    @Mapping(target = "createdAt", expression = MapStructs.Expressions.ZONED_DATE_TIME_NOW)
    @Mapping(target = "description", source = "description", qualifiedBy = StringMapper.NullableStringToNotNullString.class)
    ArticleCategoryEntity toEntity(CreateOrUpdateArticleCategoryModel model);

    /**
     * Map the specified entities to models.
     *
     * @param entities The entities to map
     *
     * @return The corresponding models
     */
    List<ArticleCategoryModel> toModels(List<ArticleCategoryEntity> entities);

    /**
     * Update the specified entity with the value of the specified model.
     *
     * @param entity The entity to udpate
     * @param model  The model which contains the data
     */
    @Mapping(target = "lastUpdateAt", expression = MapStructs.Expressions.ZONED_DATE_TIME_NOW)
    @Mapping(target = "description", source = "description", qualifiedBy = StringMapper.NullableStringToNotNullString.class)
    void updateEntity(@MappingTarget ArticleCategoryEntity entity, CreateOrUpdateArticleCategoryModel model);
}
