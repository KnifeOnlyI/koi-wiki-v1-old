package fr.koi.wikiapi.service.user;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.security.interfaces.RSAPublicKey;

/**
 * Component to manage JWT tokens.
 */
@Component
public class JwtService {
    /**
     * The authorized token issuer value.
     */
    private final String authorizedTokenIssuer;

    /**
     * The issuer for generated tokens.
     */
    private final String tokenIssuer;

    /**
     * Create a new instance.
     *
     * @param keycloakHost          The keycloak host url
     * @param keycloakRealm         The keycloak realm name
     * @param authorizedTokenIssuer The authorized token issuer
     */
    public JwtService(
        @Value("${koi-wiki.security.keycloak.host}") String keycloakHost,
        @Value("${koi-wiki.security.keycloak.realm}") String keycloakRealm,
        @Value("${koi-wiki.security.keycloak.authorized-token-issuer}") String authorizedTokenIssuer
    ) {
        this.authorizedTokenIssuer = String.format("%s/realms/%s", authorizedTokenIssuer, keycloakRealm);
        this.tokenIssuer = String.format("%s/realms/%s", keycloakHost, keycloakRealm);
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
            throw new RuntimeException("Unauthorized issuer: " + token.getIssuer());
        }

        return this.tokenIssuer + "/protocol/openid-connect/certs";
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