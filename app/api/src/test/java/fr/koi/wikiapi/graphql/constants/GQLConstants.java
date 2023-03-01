package fr.koi.wikiapi.graphql.constants;

/**
 * Contains all graphql constants for tests.
 */
public final class GQLConstants {
    /**
     * Hidden constructor.
     */
    private GQLConstants() {
    }

    public static final class Author {
        public static final String ALL_FIELDS = "id username";
    }

    public static final class ArticleCategory {
        public static final String ALL_FIELDS = "id name description createdAt lastUpdateAt deletedAt";
        public static final String ALL_PAGE_FIELDS = """
            content {%s} number totalPages totalElements""".formatted(ALL_FIELDS);
    }

    public static final class Article {
        public static final String ALL_FIELDS = """
            id title description content isArchived author {%s} categories {%s} createdAt lastUpdateAt deletedAt
            """.formatted(Author.ALL_FIELDS, ArticleCategory.ALL_FIELDS);

        public static final String ALL_PAGE_FIELDS = """
            content {%s} number totalPages totalElements""".formatted(ALL_FIELDS);
    }
}
