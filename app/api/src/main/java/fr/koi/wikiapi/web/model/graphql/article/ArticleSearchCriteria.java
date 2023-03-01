package fr.koi.wikiapi.web.model.graphql.article;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represent criteria to search articles.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArticleSearchCriteria {
    /**
     * The page number.
     */
    private int page = 0;

    /**
     * The page size.
     */
    private int pageSize = 10;

    /**
     * The results sorting.
     */
    private String sort = "id:asc";

    /**
     * The query string value.
     */
    private String q;

    /**
     * The author criteria.
     */
    private String authorId;

    /**
     * TRUE to include archived entities in the results, FALSE otherwise.
     */
    private Boolean archived;

    /**
     * TRUE to include deleted entities in the results, FALSE otherwise.
     */
    private Boolean deleted;
}
