package fr.koi.wikiapi.constants;

/**
 * Contains all API urls.
 */
public final class Urls {
    /**
     * The base url.
     */
    public static final String BASE = "/api";

    /**
     * The version url segment.
     */
    public static final String VERSION = BASE + "/v1";

    /**
     * Hidden constructor.
     */
    private Urls() {
    }

    /**
     * Contains all article category urls.
     */
    public static final class ArticleCategory {
        /**
         * The base url.
         */
        public static final String BASE = VERSION + "/articles-categories";

        /**
         * The url for unique selection.
         */
        public static final String UNIQUE = BASE + "/{id}";
    }

    /**
     * Contains all article urls.
     */
    public static final class Article {
        /**
         * The base url.
         */
        public static final String BASE = VERSION + "/articles";

        /**
         * The url for unique selection.
         */
        public static final String UNIQUE = BASE + "/{id}";
    }
}
