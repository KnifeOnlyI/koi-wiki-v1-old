package fr.koi.wikiapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * Represent an article category in the database.
 */
@Entity
@Table(name = "article_category")
@Getter
@Setter
@Accessors(chain = true)
public class ArticleCategoryEntity {
    /**
     * The ID.
     */
    @Id
    @SequenceGenerator(name = "article_category_id_gen", sequenceName = "article_category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_category_id_gen")
    private Long id;

    /**
     * The name.
     */
    @Column
    private String name;

    /**
     * The description.
     */
    @Column
    private String description;

    /**
     * The creation date.
     */
    @Column
    private ZonedDateTime createdAt;

    /**
     * The last update date.
     */
    @Column
    private ZonedDateTime lastUpdateAt;

    /**
     * The deletion date.
     */
    @Column
    private ZonedDateTime deletedAt;
}
