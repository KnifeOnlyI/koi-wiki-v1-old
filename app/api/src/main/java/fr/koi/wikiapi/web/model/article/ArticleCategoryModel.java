package fr.koi.wikiapi.web.model.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * Represent a public web model of an article category.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ArticleCategoryModel {
    /**
     * The ID.
     */
    private Long id;

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

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
