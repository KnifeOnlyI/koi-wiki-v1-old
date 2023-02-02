package fr.koi.wikiapi.service;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.koi.wikiapi.dao.KeycloakToken;
import fr.koi.wikiapi.web.controller.model.AuthenticationModel;
import fr.koi.wikiapi.web.controller.model.RefreshTokenModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.security.interfaces.RSAPublicKey;

/**
 * Component to manage JWT tokens.
 */
@Component
public class JwtService {
    /**
     * The keycloak url to get tokens.
     */
    private final URI keycloakTokenUrl;

    /**
     * The HTTP headers for form url encoded body.
     */
    private final HttpHeaders formUrlEncodedHeaders = new HttpHeaders();

    /**
     * The rest template to perform HTTP requests.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * The keycloak client ID.
     */
    private final String keycloakClientId;

    /**
     * The authorized token issuer value.
     */
    private final String authorizedTokenIssuer;

    /**
     * Create a new instance.
     *
     * @param keycloakHost     The keycloak host url
     * @param keycloakRealm    The keycloak realm name
     * @param keycloakClientId The keycloak client ID
     *
     * @throws URISyntaxException If the built keycloak token URL is malformated
     */
    public JwtService(
        @Value("${koi-wiki.security.keycloak.host}") String keycloakHost,
        @Value("${koi-wiki.security.keycloak.realm}") String keycloakRealm,
        @Value("${koi-wiki.security.keycloak.client-id}") String keycloakClientId
    ) throws URISyntaxException {
        this.authorizedTokenIssuer = String.format("%s/realms/%s", keycloakHost, keycloakRealm);
        this.keycloakClientId = keycloakClientId;
        this.keycloakTokenUrl = new URI(String.format(
            "%s/realms/%s/protocol/openid-connect/token",
            keycloakHost,
            keycloakRealm
        ));

        this.formUrlEncodedHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    /**
     * Get the certificate URL of the specified token.
     *
     * @param token The token to check
     *
     * @return The URL
     */
    private String getKeycloakCertificateUrl(DecodedJWT token) {
        if (!token.getIssuer().equals(this.authorizedTokenIssuer)) {
            throw new RuntimeException(); // TODO: Manage errors
        }

        return token.getIssuer() + "/protocol/openid-connect/certs";
    }

    /**
     * Load the public key of the specified token.
     *
     * @param token The token to check
     *
     * @return The public key
     *
     * @throws MalformedURLException If the URL of location where to fetch the certificate is malformed
     * @throws JwkException          If JWK url provider throw an error
     */
    private RSAPublicKey loadPublicKey(DecodedJWT token) throws MalformedURLException, JwkException {
        String url = this.getKeycloakCertificateUrl(token);

        JwkProvider provider = new UrlJwkProvider(new URL(url));

        return (RSAPublicKey) provider.get(token.getKeyId()).getPublicKey();
    }

    /**
     * Get a new token corresponding to the specified authentication data.
     *
     * @param authenticationData The authentication data to check
     *
     * @return The new keycloak token
     */
    public KeycloakToken getNewToken(AuthenticationModel authenticationData) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", authenticationData.getUsername());
        map.add("password", authenticationData.getPassword());
        map.add("grant_type", "password");
        map.add("client_id", this.keycloakClientId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, this.formUrlEncodedHeaders);

        ResponseEntity<KeycloakToken> response = this.restTemplate.postForEntity(
            this.keycloakTokenUrl,
            request,
            KeycloakToken.class
        );

        return response.getBody();
    }

    /**
     * Get a new token corresponding to the specified refresh token data.
     *
     * @param refreshTokenData The refresh token data
     *
     * @return The new keycloak token
     */
    public KeycloakToken refreshToken(RefreshTokenModel refreshTokenData) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", refreshTokenData.getUsername());
        map.add("password", refreshTokenData.getPassword());
        map.add("grant_type", "refresh_token");
        map.add("client_id", this.keycloakClientId);
        map.add("refresh_token", refreshTokenData.getToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, this.formUrlEncodedHeaders);

        ResponseEntity<KeycloakToken> response = this.restTemplate.postForEntity(
            this.keycloakTokenUrl,
            request,
            KeycloakToken.class
        );

        return response.getBody();
    }

    /**
     * Check if the specified token is valid and return a model of it.
     *
     * @param token The token to check
     *
     * @return The corresponding model
     */
    public DecodedJWT validate(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            RSAPublicKey publicKey = this.loadPublicKey(jwt);

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwt.getIssuer())
                .build();

            verifier.verify(token);

            return jwt;
        } catch (Exception e) {
            throw new InvalidParameterException(String.format("JWT validation failed : %s", e.getMessage()));
        }
    }
}