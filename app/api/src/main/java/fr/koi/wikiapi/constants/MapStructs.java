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

    /**
     * Contains all qualifier name constants.
     */
    public static final class QualifierName {
        /**
         * The qualifier name to identify the function that map a nullable string to a not null string.
         */
        public static final String NULLABLE_STRING_TO_STRING = "nullableStringToString";

        /**
         * Hidden constructor.
         */
        private QualifierName() {
        }
    }
}
