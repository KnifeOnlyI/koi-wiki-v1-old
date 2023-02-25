import {Routes} from '@angular/router';
import {HomeComponent} from './features/pages/home/home.component';
import {NotFoundComponent} from './features/pages/not-found/not-found.component';
import {ARTICLE_CATEGORY_ROUTES} from './routes/article-category.routes';

const ROUTES: Routes = [];

ROUTES.push({
  path: '',
  component: HomeComponent,
});

ARTICLE_CATEGORY_ROUTES.forEach(route => ROUTES.push(route));

ROUTES.push({
    path: 'not-found',
    component: NotFoundComponent,
  },
  {
    path: '**',
    redirectTo: 'not-found',
  },
);

export default ROUTES;
