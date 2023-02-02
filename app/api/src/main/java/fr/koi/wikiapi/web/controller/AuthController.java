package fr.koi.wikiapi.web.controller;

import fr.koi.wikiapi.dao.KeycloakToken;
import fr.koi.wikiapi.service.JwtService;
import fr.koi.wikiapi.web.controller.model.AuthenticationModel;
import fr.koi.wikiapi.web.controller.model.RefreshTokenModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtUtils;

    @PostMapping("/api/v1/auth/token")
    public KeycloakToken token(@Valid @RequestBody AuthenticationModel authenticationData) {
        return jwtUtils.getNewToken(authenticationData);
    }

    @PostMapping("/api/v1/auth/refresh-token")
    public KeycloakToken refreshToken(@Valid @RequestBody RefreshTokenModel refreshTokenData) {
        return jwtUtils.refreshToken(refreshTokenData);
    }

    @PostMapping("/api/v1/auth/logout")
    public Map<String, String> logout() {
        Map<String, String> value = new HashMap<>();

        value.put("logout", "YES");

        return value;
    }
}
