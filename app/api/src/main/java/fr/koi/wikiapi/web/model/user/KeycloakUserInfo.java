package fr.koi.wikiapi.web.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represent a user info data come from keycloak REST api.
 */
@Getter
@Setter
@Accessors(chain = true)
public class KeycloakUserInfo {
    /**
     * The user ID.
     */
    private String id;

    /**
     * The username.
     */
    private String username;

    /**
     * The email.
     */
    private String email;
}
