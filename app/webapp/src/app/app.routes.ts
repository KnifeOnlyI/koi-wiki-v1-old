import {Routes} from '@angular/router';
import {HomeComponent} from './features/pages/home/home.component';
import {NotFoundComponent} from './features/pages/not-found/not-found.component';
import {AuthGuard} from './core/auth/guard/auth.guard';
import {
  ArticleCategoryListComponent,
} from './features/components/article-category-list/article-category-list.component';
import {
  ArticleCategoryEditorComponent,
} from './features/components/article-category-editor/article-category-editor.component';
import {Role} from './core/auth/constants/role';

export const ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'articles-categories',
    component: ArticleCategoryListComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        Role.SEARCH_ARTICLE_CATEGORY,
        Role.READ_ARTICLE_CATEGORY,
      ],
    },
  },
  {
    path: 'articles-categories/create',
    component: ArticleCategoryEditorComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        Role.CREATE_ARTICLE_CATEGORY,
        Role.READ_ARTICLE_CATEGORY,
      ],
    },
  },
  {
    path: 'articles-categories/edit/:id',
    component: ArticleCategoryEditorComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        Role.UPDATE_ARTICLE_CATEGORY,
        Role?.READ_ARTICLE_CATEGORY,
      ],
    },
  },
  {
    path: 'not-found',
    component: NotFoundComponent,
  },
  {
    path: '**',
    redirectTo: 'not-found',
  },
];
