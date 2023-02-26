import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {KeycloakAuthGuard, KeycloakService} from 'keycloak-angular';
import {UserService} from '../service/user.service';

/**
 * The guard to manage authentication.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  /**
   * Create a new instance.
   *
   * @param router The router
   * @param keycloak The service to manage keycloak
   * @param userService The service to manage users
   */
  constructor(
    protected override readonly router: Router,
    private readonly keycloak: KeycloakService,
    private readonly userService: UserService
  ) {
    super(router, keycloak);
  }

  async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {
    if (!this.userService.isAuthenticated) {
      await this.userService.login(window.location.origin + state.url);
    }

    return this.userService.hasRoles(route.data['roles']);
  }
}
