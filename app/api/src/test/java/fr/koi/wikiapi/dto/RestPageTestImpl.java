package fr.koi.wikiapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Implementation for tests of Page.
 *
 * @param <T> The content type
 */
public class RestPageTestImpl<T> extends PageImpl<T> {
    /**
     * Complete Constructor for jackson json.
     *
     * @param content          content
     * @param number           number
     * @param size             size
     * @param totalElements    totalElements
     * @param pageable         pageable
     * @param last             last
     * @param totalPages       totalPages
     * @param sort             sort
     * @param first            first
     * @param numberOfElements numberOfElements
     * @param empty            empty
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    @SuppressWarnings("ParameterNumber")
    public RestPageTestImpl(@JsonProperty("content") final List<T> content, @JsonProperty("number") final int number, @JsonProperty("size") final int size, @JsonProperty("totalElements") final Long totalElements, @JsonProperty("pageable") final JsonNode pageable, @JsonProperty("last") final boolean last, @JsonProperty("totalPages") final int totalPages, @JsonProperty("sort") final JsonNode sort, @JsonProperty("first") final boolean first, @JsonProperty("numberOfElements") final int numberOfElements, @JsonProperty("empty") final boolean empty) {
        super(content, PageRequest.of(number, size), totalElements);
    }

}
