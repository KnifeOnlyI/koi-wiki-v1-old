package fr.koi.wikiapi.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * The base class for all controller (integration) tests.
 */
public class BaseControllerTest extends BaseTest {
    /**
     * The rest template to manage external HTTP queries.
     */
    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * The keycloak URL.
     */
    @Value("${koi-wiki.security.keycloak.host}")
    private String keycloakUrl;

    /**
     * The keycloak realm.
     */
    @Value("${koi-wiki.security.keycloak.realm}")
    private String keycloakRealm;

    /**
     * The keycloak client ID.
     */
    @Value("${koi-wiki.security.keycloak.client-id}")
    private String keycloakClientId;

    /**
     * Get an access token for the specified user.
     *
     * @param username The username
     * @param password The password
     *
     * @return The fetch access token
     */
    protected String getAccessToken(final String username, final String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("grant_type", Collections.singletonList("password"));
        map.put("client_id", Collections.singletonList(this.keycloakClientId));
        map.put("username", Collections.singletonList(username));
        map.put("password", Collections.singletonList(password));

        Map<String, String> response = BaseControllerTest.restTemplate.postForObject(
            String.format("%s/realms/%s/protocol/openid-connect/token", this.keycloakUrl, this.keycloakRealm),
            new HttpEntity<>(map, httpHeaders),
            Map.class
        );

        assert response != null;

        return response.get("access_token");
    }

    /**
     * Get a root token.
     *
     * @return The root token
     */
    protected String getRootToken() {
        return this.getAccessToken("root", "root");
    }
}
