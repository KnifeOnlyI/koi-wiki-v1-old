import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";

/**
 * The service to manage users.
 */
@Injectable({providedIn: 'root'})
export class UserService {
  /**
   * Create a new instance.
   *
   * @param keycloakService The service to manage keycloak
   */
  constructor(private readonly keycloakService: KeycloakService) {
  }

  /**
   * Check if user is authenticated.
   *
   * @return TRUE if the user is authenticated, FALSE otherwise
   */
  async isAuthenticated(): Promise<boolean> {
    const token = await this.keycloakService.getToken();

    if (!token) {
      return false;
    }

    await this.keycloakService.loadUserProfile()

    return this.keycloakService.isLoggedIn();
  }

  /**
   * Get the username.
   *
   * @return The username (NULL if not available)
   */
  async getUsername(): Promise<string | null> {
    if (!await this.isAuthenticated()) {
      return null;
    }

    return this.keycloakService.getUsername();
  }

  /**
   * Get user roles.
   *
   * @return The user roles
   */
  getRoles(): Array<string> {
    return this.keycloakService.getUserRoles(true) ?? new Array<string>();
  }

  /**
   * Check if the authenticated user has the specified roles.
   *
   * @param requiredRoles The required roles
   *
   * @return TRUE if the authenticated user has the specified roles, FALSE otherwise
   */
  hasRole(requiredRoles?: Array<string> | null): boolean {
    if (!requiredRoles || !requiredRoles.length) {
      return true;
    }

    return requiredRoles.every(role => this.getRoles().includes(role));
  }

  /**
   * Perform a logout.
   *
   * @param redirectUri The redirect URI after logout
   */
  async logout(redirectUri?: string): Promise<void> {
    return this.keycloakService.logout(redirectUri);
  }

  /**
   * Perform a login.
   *
   * @param redirectUri The redirect URI after login
   */
  async login(redirectUri?: string): Promise<void> {
    return this.keycloakService.login({redirectUri}).then();
  }
}
