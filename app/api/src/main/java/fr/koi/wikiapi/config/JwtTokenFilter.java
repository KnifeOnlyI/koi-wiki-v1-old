package fr.koi.wikiapi.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import fr.koi.wikiapi.dto.user.KoiWikiUserDetails;
import fr.koi.wikiapi.service.user.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The filter to manage the JWT token send in request.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtUtils;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.getToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT jwt = jwtUtils.validate(token);

        String username = jwt.getClaim("preferred_username").asString();
        List<String> realmAccess = (List<String>) jwt.getClaim("realm_access").asMap().get("roles");
        List<String> resourceAccess = (List<String>) ((Map<String, Object>) jwt.getClaim("resource_access").asMap().get("account")).get("roles");
        List<String> permissions = new ArrayList<>();

        permissions.addAll(realmAccess);
        permissions.addAll(resourceAccess);

        KoiWikiUserDetails userDetails = new KoiWikiUserDetails(username, permissions);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * Get the token (Bearer ...) in the specified request
     *
     * @param request The request to check
     *
     * @return The founded token (NULL if no header value available)
     */
    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        String token = null;

        if (authorization != null) {
            token = authorization.split(" ")[1];
        }

        return token;
    }
}
