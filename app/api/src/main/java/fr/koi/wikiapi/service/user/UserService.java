package fr.koi.wikiapi.service.user;

import fr.koi.wikiapi.dto.exception.ForbiddenException;
import fr.koi.wikiapi.dto.user.KeycloakToken;
import fr.koi.wikiapi.web.model.user.KeycloakUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The service to manage users.
 */
@Service
public class UserService {
    /**
     * The rest template to perform HTTP requests.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * The URL to get an admin token from master realm.
     */
    private final URI keycloakAdminMasterNewTokenUrl;

    /**
     * The keycloak host.
     */
    private final String keycloakHost;

    /**
     * The username of master realm admin user.
     */
    @Value("${koi-wiki.security.keycloak.master-admin-username}")
    private String keycloakMasterAdminUsername;

    /**
     * The password of master realm admin user.
     */
    @Value("${koi-wiki.security.keycloak.master-admin-password}")
    private String keycloakMasterAdminPassword;

    /**
     * The keycloak realm name.
     */
    @Value("${koi-wiki.security.keycloak.realm}")
    private String keycloakRealm;

    /**
     * Create a new instance.
     *
     * @param keycloakHost The keycloak host
     *
     * @throws URISyntaxException If an invalid URL is built
     */
    public UserService(@Value("${koi-wiki.security.keycloak.host}") String keycloakHost) throws URISyntaxException {
        this.keycloakHost = keycloakHost;

        this.keycloakAdminMasterNewTokenUrl = new URI(String.format(
            "%s/realms/%s/protocol/openid-connect/token",
            this.keycloakHost,
            "master"
        ));
    }

    /**
     * Assert the user is logged.
     */
    public void assertUserLogged() {
        if (this.getUserId() == null) {
            throw new ForbiddenException();
        }
    }

    /**
     * Get the user ID.
     *
     * @return The user ID
     */
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null ? authentication.getName() : null;
    }

    /**
     * Get the username of the specified user ID.
     *
     * @param id The ID of user to check
     *
     * @return The response that contains the corresponding username
     */
    public String getUsername(final String id) {
        KeycloakToken token = this.getAdminTokenOfMasterRealm();
        KeycloakUserInfo userInfo = this.getKeycloakUserInfo(id, token.getAccessToken());

        return userInfo != null ? userInfo.getUsername() : null;
    }

    /**
     * Check if the connected user has the specified role.
     *
     * @param role The expected role
     *
     * @return TRUE if the user has the specified role, FALSE otherwise
     */
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || StringUtils.isBlank(role)) {
            return false;
        }

        return authentication
            .getAuthorities()
            .stream()
            .anyMatch(authority -> StringUtils.equalsIgnoreCase(authority.getAuthority(), role));
    }

    /**
     * Get a new token of admin user in master realm.
     *
     * @return The new keycloak token
     */
    private KeycloakToken getAdminTokenOfMasterRealm() {
        HttpHeaders formUrlEncodedHeaders = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        formUrlEncodedHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        map.add("username", this.keycloakMasterAdminUsername);
        map.add("password", this.keycloakMasterAdminPassword);
        map.add("grant_type", "password");
        map.add("client_id", "admin-cli");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, formUrlEncodedHeaders);

        ResponseEntity<KeycloakToken> response = this.restTemplate.postForEntity(
            this.keycloakAdminMasterNewTokenUrl,
            request,
            KeycloakToken.class
        );

        return response.getBody();
    }

    /**
     * Get a keycloak user info of the specified user.
     *
     * @param userId             The ID of user to check
     * @param authorizationToken The authorization token
     *
     * @return The keycloak user info (NULL if no user can be found)
     */
    private KeycloakUserInfo getKeycloakUserInfo(final String userId, String authorizationToken) {
        HttpHeaders headers = new HttpHeaders();
        URI requestUrl;

        try {
            requestUrl = new URI(String.format(
                "%s/admin/realms/%s/users/%s",
                this.keycloakHost,
                this.keycloakRealm,
                userId
            ));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authorizationToken);

        try {
            ResponseEntity<KeycloakUserInfo> response = this.restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                }
            );

            if (response.getStatusCode().isError()) {
                return null;
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                return null;
            }

            throw e;
        }
    }
}
