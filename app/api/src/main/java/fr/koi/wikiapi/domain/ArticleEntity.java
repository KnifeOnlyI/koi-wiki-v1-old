package fr.koi.wikiapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represent an article in the database.
 */
@Entity
@Table(name = "article")
@Getter
@Setter
@Accessors(chain = true)
public class ArticleEntity {
    /**
     * The ID.
     */
    @Id
    @SequenceGenerator(name = "article_id_gen", sequenceName = "article_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_gen")
    private Long id;

    /**
     * The name.
     */
    @Column
    private String title;

    /**
     * The description.
     */
    @Column
    private String description;

    /**
     * The content.
     */
    @Column
    private String content;

    /**
     * The flag to indicates if the article is archived.
     */
    @Column
    private Boolean isArchived;

    /**
     * The author ID.
     */
    @Column
    private String authorId;

    /**
     * The categories.
     */
    @ManyToMany
    @JoinTable(
        name = "article_article_category",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "article_category_id")
    )
    private List<ArticleCategoryEntity> categories;

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
