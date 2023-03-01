package fr.koi.wikiapi.graphql.response.article;

import fr.koi.wikiapi.web.model.graphql.article.ArticleModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GQLArticleModel {
    private GQLPageArticle searchArticles;
    private ArticleModel getArticleById;
    private ArticleModel createArticle;
    private ArticleModel updateArticle;
    private Long deleteArticle;
}
