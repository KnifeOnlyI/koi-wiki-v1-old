package fr.koi.wikiapi.constants;

/**
 * Contains all roles.
 */
public final class Roles {
    /**
     * Hidden constructor.
     */
    private Roles() {
    }

    /**
     * Contains all roles associated to article category.
     */
    public static final class ArticleCategory {
        public static final String CREATE = "create-article-category";
        public static final String READ = "read-article-category";
        public static final String UPDATE = "update-article-category";
        public static final String DELETE = "delete-article-category";
        public static final String SEARCH = "search-article-category";

        public static final String READ_DELETED = "read-deleted-article-category";
        public static final String UPDATE_DELETED = "update-deleted-article-category";
        public static final String DELETE_DELETED = "delete-deleted-article-category";
    }

    /**
     * Contains all roles associated to article.
     */
    public static final class Article {
        public static final String CREATE = "create-article";
        public static final String READ = "read-article";
        public static final String UPDATE = "update-article";
        public static final String DELETE = "delete-article";
        public static final String SEARCH = "search-article";

        public static final String READ_OTHER = "read-other-article";
        public static final String READ_ARCHIVED = "read-archived-article";
        public static final String READ_OTHER_ARCHIVED = "read-other-archived-article";
        public static final String READ_DELETED = "read-deleted-article";
        public static final String READ_OTHER_DELETED = "read-other-deleted-article";

        public static final String UPDATE_OTHER = "update-other-article";
        public static final String UPDATE_ARCHIVED = "update-archived-article";
        public static final String UPDATE_OTHER_ARCHIVED = "update-other-archived-article";
        public static final String UPDATE_DELETED = "update-deleted-article";
        public static final String UPDATE_OTHER_DELETED = "update-other-deleted-article";

        public static final String DELETE_OTHER = "delete-other-article";
        public static final String DELETE_ARCHIVED = "delete-archived-article";
        public static final String DELETE_OTHER_ARCHIVED = "delete-other-archived-article";
        public static final String DELETE_DELETED = "delete-deleted-article";
        public static final String DELETE_OTHER_DELETED = "delete-other-deleted-article";

        public static final String SEARCH_OTHER = "search-other-article";
        public static final String SEARCH_ARCHIVED = "search-archived-article";
        public static final String SEARCH_OTHER_ARCHIVED = "search-other-archived-article";
        public static final String SEARCH_DELETED = "search-deleted-article";
        public static final String SEARCH_OTHER_DELETED = "search-other-deleted-article";
    }
}
