import {Routes} from '@angular/router';
import {ProfileComponent} from "./features/profile/profile.component";
import {HomeComponent} from "./features/home/home.component";
import {NotFoundComponent} from "./features/not-found/not-found.component";
import {AuthGuard} from "./core/auth/guard/auth.guard";
import {ArticlesComponent} from "./features/articles/articles.component";

export const ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        'view-profile'
      ]
    }
  },
  {
    path: 'articles',
    component: ArticlesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'not-found',
    component: NotFoundComponent
  }, {
    path: '**',
    redirectTo: 'not-found'
  },
];
