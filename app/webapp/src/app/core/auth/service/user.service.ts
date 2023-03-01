import {Injectable} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {Role} from '../constants/role';
import {Objects} from '../../../shared/utils/objects';
import {JwtService} from '../../service/jwt.service';
import {JwtToken} from '../models/jwt-token';
import {HttpClient} from '@angular/common/http';

/**
 * The service to manage users.
 */
@Injectable()
export class UserService {
  /**
   * The flag to indicates if the user is authenticated.
   */
  public readonly isAuthenticated: boolean;
  /**
   * The username of currently logged user.
   */
  public readonly username?: string;
  /**
   * The ID of currently logged user.
   */
  public readonly userId?: string;

  /**
   * The token of the currently logger user.
   */
  private readonly token: JwtToken | null;

  /**
   * Create a new instance.
   *
   * @param keycloakService The service to manage keycloak
   * @param jwtService The service to manage JWT tokens
   * @param http The service to manage HTTP requests
   */
  constructor(
    private readonly keycloakService: KeycloakService,
    private readonly jwtService: JwtService,
    private readonly http: HttpClient,
  ) {
    this.token = this.jwtService.decode(this.keycloakService.getKeycloakInstance().token);

    this.isAuthenticated = !Objects.isNull(this.token);
    this.userId = this.token?.payload.sub;
    this.username = this.token?.payload.preferred_username;
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
