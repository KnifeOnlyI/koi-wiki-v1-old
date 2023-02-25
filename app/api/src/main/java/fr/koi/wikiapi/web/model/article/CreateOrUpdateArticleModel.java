package fr.koi.wikiapi.web.model.article;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Represent a public web model to create or update an article.
 */
@Getter
@Setter
@Accessors(chain = true)
public class CreateOrUpdateArticleModel {
    /**
     * The name.
     */
    @NotBlank
    @Length(max = 255)
    private String title;

    /**
     * The description.
     */
    @Length(max = 1000)
    private String description;

    /**
     * The content
     */
    @Length(max = 65000)
    private String content;

    /**
     * The associated categories.
     */
    private List<Long> categories;
}
