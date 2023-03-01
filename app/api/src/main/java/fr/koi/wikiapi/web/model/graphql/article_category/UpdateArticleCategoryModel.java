package fr.koi.wikiapi.web.model.graphql.article_category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * Represent a public web model to create or update an article category.
 */
@Getter
@Setter
@Accessors(chain = true)
public class UpdateArticleCategoryModel {
    @NotNull
    private Long id;

    /**
     * The name.
     */
    @NotBlank
    @Length(max = 255)
    private String name;

    /**
     * The description.
     */
    @Length(max = 1000)
    private String description;
}
