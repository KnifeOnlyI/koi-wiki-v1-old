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

    private static final class Base {
        /**
         * To create an entity.
         */
        public static final String CREATE = "create";

        /**
         * To read an entity.
         */
        public static final String READ = "read";

        /**
         * To read a deleted entity.
         */
        public static final String READ_DELETED = "read-deleted";

        /**
         * To update an entity.
         */
        public static final String UPDATE = "update";

        /**
         * To update a deleted entity.
         */
        public static final String UPDATE_DELETED = "update-deleted";

        /**
         * To delete an entity.
         */
        public static final String DELETE = "delete";

        /**
         * To delete definitively a deleted entity.
         */
        public static final String DELETE_DELETED = "delete-deleted";

        /**
         * To search entities.
         */
        public static final String SEARCH = "search";

        /**
         * To search a deleted entities.
         */
        public static final String SEARCH_DELETED = "search-deleted";
    }

    /**
     * Contains all roles associated to article category.
     */
    public static final class ArticleCategory {
        private static final String BASE = "article-category";

        public static final String CREATE = Base.CREATE + "-" + BASE;

        public static final String READ = Base.READ + "-" + BASE;

        public static final String READ_DELETED = Base.READ_DELETED + "-" + BASE;

        public static final String UPDATE = Base.UPDATE + "-" + BASE;

        public static final String UPDATE_DELETED = Base.UPDATE_DELETED + "-" + BASE;

        public static final String DELETE = Base.DELETE + "-" + BASE;

        public static final String DELETE_DELETED = Base.DELETE_DELETED + "-" + BASE;

        public static final String SEARCH = Base.SEARCH + "-" + BASE;

        public static final String SEARCH_DELETED = Base.SEARCH_DELETED + "-" + BASE;
    }
}
