import {Injectable} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {Role} from '../constants/role';
import {Objects} from '../../../shared/utils/objects';

/**
 * The service to manage users.
 */
@Injectable({providedIn: 'root'})
export class UserService {
  public static isAuthenticated = false;

  /**
   * Create a new instance.
   *
   * @param keycloakService The service to manage keycloak
   */
  constructor(private readonly keycloakService: KeycloakService) {
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
   * Check if the authenticated user has the specified role.
   *
   * @param requiredRole The required role
   *
   * @return TRUE if the authenticated user has the specified role, FALSE otherwise
   */
  hasRole(requiredRole?: Role | null) {
    if (!requiredRole) {
      return true;
    }

    return this.hasRoles([requiredRole]);
  }

  /**
   * Check if the authenticated user has the specified roles.
   *
   * @param requiredRoles The required roles
   *
   * @return TRUE if the authenticated user has the specified roles, FALSE otherwise
   */
  hasRoles(requiredRoles?: Array<Role> | null): boolean {
    if (Objects.isNull(requiredRoles) || !requiredRoles?.length) {
      return true;
    }

    return requiredRoles.every(role => this.getRoles().includes(role));
  }

  /**
   * Perform a logout.
   *
   * @param redirectUri The redirect URI after logout
   */
  logout(redirectUri?: string): void {
    this.keycloakService.logout(redirectUri).then();
  }

  /**
   * Perform a login.
   *
   * @param redirectUri The redirect URI after login
   */
  login(redirectUri?: string): void {
    this.keycloakService.login({redirectUri}).then();
  }
}
