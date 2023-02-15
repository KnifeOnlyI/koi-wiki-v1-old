package fr.koi.wikiapi.web.utils;

import org.springframework.http.HttpHeaders;

/**
 * An HTTP headers builder.
 */
public class HttpHeadersBuilder {
    /**
     * The headers.
     */
    private final HttpHeaders headers = new HttpHeaders();

    /**
     * Add the authorization header.
     *
     * @param token The token to add
     *
     * @return this
     */
    public HttpHeadersBuilder authorization(String token) {
        this.headers.add("Authorization", String.format("Bearer %s", token));

        return this;
    }

    /**
     * Build the http headers.
     *
     * @return The http headers
     */
    public HttpHeaders build() {
        return this.headers;
    }
}
