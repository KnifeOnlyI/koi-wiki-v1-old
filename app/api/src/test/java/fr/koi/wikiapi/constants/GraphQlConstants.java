package fr.koi.wikiapi.constants;

public final class GraphQlConstants {
    /**
     * Hidden constructor.
     */
    private GraphQlConstants() {
    }

    public static final class ArticleCategory {
        public static final String ALL_FIELDS = "id name description createdAt lastUpdateAt deletedAt";
        public static final String ALL_PAGE_FIELDS = """
            content {%s} number totalPages totalElements""".formatted(ALL_FIELDS);
    }
}
