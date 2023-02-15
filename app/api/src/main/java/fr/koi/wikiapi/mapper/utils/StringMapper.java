package fr.koi.wikiapi.mapper.utils;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A mapper for strings.
 */
public final class StringMapper {
    /**
     * Hidden constructor.
     */
    private StringMapper() {
    }

    /**
     * Map a nullable string to a not null string (empty string if the specified value is null, the original value otherwise).
     *
     * @param value The value to map
     *
     * @return The corresponding value
     */
    @NullableStringToNotNullString
    public static String nullableStringToNotNullString(final String value) {
        return value == null ? "" : value;
    }

    /**
     * A nullable string to not null string qualifier.
     */
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface NullableStringToNotNullString {

    }

}