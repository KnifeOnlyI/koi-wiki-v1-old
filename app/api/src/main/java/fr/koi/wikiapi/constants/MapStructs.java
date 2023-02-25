package fr.koi.wikiapi.constants;

/**
 * Contains all map struct constants.
 */
public final class MapStructs {
    /**
     * Hidden constructor.
     */
    private MapStructs() {
    }

    /**
     * Contains all expressions constants.
     */
    public static final class Expressions {
        /**
         * The java expression to create the now ZonedDateTime.
         */
        public static final String ZONED_DATE_TIME_NOW = "java(fr.koi.wikiapi.utils.TimeProvider.getInstance().nowZonedDateTime())";

        /**
         * Hidden constructor.
         */
        private Expressions() {
        }
    }
}
