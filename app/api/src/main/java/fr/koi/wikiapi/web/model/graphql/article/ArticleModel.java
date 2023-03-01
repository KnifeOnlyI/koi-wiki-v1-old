package fr.koi.wikiapi.web.model.graphql.article;

import fr.koi.wikiapi.web.model.graphql.article_category.ArticleCategoryModel;
import fr.koi.wikiapi.web.model.graphql.user.AuthorModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ArticleModel {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Boolean isArchived;
    private AuthorModel author;
    private List<ArticleCategoryModel> categories;
    private ZonedDateTime createdAt;
    private ZonedDateTime lastUpdateAt;
    private ZonedDateTime deletedAt;
}
