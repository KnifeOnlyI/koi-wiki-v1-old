package fr.koi.wikiapi.web.model.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represent a public web model of an article.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArticleModel {
    /**
     * The ID.
     */
    private Long id;

    /**
     * The name.
     */
    private String title;

    /**
     * The description.
     */
    private String description;

    /**
     * The content.
     */
    private String content;

    /**
     * The associated categories.
     */
    private List<Long> categories;

    /**
     * The creation date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createdAt;

    /**
     * The last update date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime lastUpdateAt;

    /**
     * The deletion date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime deletedAt;
}
