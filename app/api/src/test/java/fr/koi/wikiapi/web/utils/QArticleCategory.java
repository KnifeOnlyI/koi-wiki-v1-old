package fr.koi.wikiapi.web.utils;

import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class QArticleCategory {
    private QPageArticleCategory searchArticleCategories;
    private ArticleCategoryModel getArticleCategoryById;
    private ArticleCategoryModel createArticleCategory;
    private ArticleCategoryModel updateArticleCategory;
    private Long deleteArticleCategory;
}
