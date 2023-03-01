package fr.koi.wikiapi.graphql.response.article_category;

import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GQLArticleCategory {
    private GQLPageArticleCategory searchArticleCategories;
    private ArticleCategoryModel getArticleCategoryById;
    private ArticleCategoryModel createArticleCategory;
    private ArticleCategoryModel updateArticleCategory;
    private Long deleteArticleCategory;
}
