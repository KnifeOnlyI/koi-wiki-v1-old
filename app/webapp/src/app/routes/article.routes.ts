import {AuthGuard} from '../core/auth/guard/auth.guard';
import {Role} from '../core/auth/constants/role';
import {Routes} from '@angular/router';
import {ArticleListComponent} from '../features/components/article-list/article-list.component';
import {ArticleEditorComponent} from '../features/components/article-editor/article-editor.component';
import {ArticleDetailComponent} from '../features/components/article-detail/article-detail.component';

export const ARTICLE_ROUTES: Routes = [
  {
    path: 'articles',
    component: ArticleListComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        Role.SEARCH_ARTICLE,
        Role.READ_ARTICLE,
      ],
    },
  },
  {
    path: 'articles/create',
    component: ArticleEditorComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        Role.CREATE_ARTICLE,
        Role.READ_ARTICLE,
      ],
    },
  },
  {
    path: 'articles/:id',
    component: ArticleDetailComponent,
  },
  {
    path: 'articles/edit/:id',
    component: ArticleEditorComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [
        Role.UPDATE_ARTICLE,
        Role?.READ_ARTICLE,
      ],
    },
  },
];
