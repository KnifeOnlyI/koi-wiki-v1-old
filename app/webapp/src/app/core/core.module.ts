import {APP_INITIALIZER, NgModule} from '@angular/core';
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializeKeycloak} from "./auth/init/keycloak-init.factory";

@NgModule({
  declarations: [],
  imports: [
    KeycloakAngularModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    }
  ]
})
export class CoreModule {
}
