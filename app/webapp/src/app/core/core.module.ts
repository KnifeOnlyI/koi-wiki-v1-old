import {APP_INITIALIZER, NgModule} from '@angular/core';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {initializeKeycloak} from './auth/init/keycloak-init.factory';
import {SharedModule} from '../shared/shared.module';
import {MainNavbarComponent} from './main-navbar/main-navbar.component';
import {RouterLink} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {NgClass, NgIf} from '@angular/common';
import {UserService} from './auth/service/user.service';
import {JwtService} from './service/jwt.service';
import {MatTooltipModule} from '@angular/material/tooltip';

@NgModule({
  declarations: [
    MainNavbarComponent,
  ],
  imports: [
    SharedModule,
    KeycloakAngularModule,
    RouterLink,
    MatButtonModule,
    NgClass,
    NgIf,
    MatTooltipModule,
  ],
  exports: [
    MainNavbarComponent,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    UserService,
    JwtService,
  ],
})
export class CoreModule {
}
