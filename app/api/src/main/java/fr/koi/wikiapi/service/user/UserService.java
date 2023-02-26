package fr.koi.wikiapi.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * The service to manage users.
 */
@Service
public class UserService {
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
}
