package fr.koi.wikiapi.graphql.response.article;

import fr.koi.wikiapi.web.model.graphql.article.ArticleModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class GQLPageArticle {
    private List<ArticleModel> content;
    private Integer number;
    private Integer totalPages;
    private Integer totalElements;
}
