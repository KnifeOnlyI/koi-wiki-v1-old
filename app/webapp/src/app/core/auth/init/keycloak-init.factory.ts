import {KeycloakService} from "keycloak-angular";
import {environment} from "../../../../environments/environment";
import {UserService} from "../service/user.service";

/**
 * Method to configure and initialize keycloak.
 *
 * @param keycloak The keycloak service
 */
export function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.baseKeycloakUrl,
        realm: environment.keycloakRealm,
        clientId: environment.keycloakClientId,
      },
      initOptions: {
        onLoad: 'check-sso',
        checkLoginIframe: false
      }
    }).then(authenticated => UserService.isAuthenticated = authenticated);
}
