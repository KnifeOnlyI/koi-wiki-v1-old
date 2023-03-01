package fr.koi.wikiapi.web.model.graphql.article_category;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class ArticleCategoryModel {
    private Long id;
    private String name;
    private String description;
    private ZonedDateTime createdAt;
    private ZonedDateTime lastUpdateAt;
    private ZonedDateTime deletedAt;
}
