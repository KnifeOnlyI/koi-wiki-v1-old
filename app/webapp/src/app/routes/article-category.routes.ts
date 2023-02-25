import {
  ArticleCategoryListComponent,
} from '../features/components/article-category-list/article-category-list.component';
import {AuthGuard} from '../core/auth/guard/auth.guard';
import {Role} from '../core/auth/constants/role';
import {
  ArticleCategoryEditorComponent,
} from '../features/components/article-category-editor/article-category-editor.component';
import {Routes} from '@angular/router';

export const ARTICLE_CATEGORY_ROUTES: Routes = [
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
];
