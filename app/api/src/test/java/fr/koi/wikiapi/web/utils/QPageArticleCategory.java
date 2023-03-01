package fr.koi.wikiapi.web.utils;

import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class QPageArticleCategory {
    private List<ArticleCategoryModel> content;
    private Integer number;
    private Integer totalPages;
    private Integer totalElements;
}
