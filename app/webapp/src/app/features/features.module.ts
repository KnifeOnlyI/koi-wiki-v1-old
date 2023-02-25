import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterLink, RouterOutlet} from '@angular/router';
import {AsyncPipe, DatePipe, JsonPipe, NgForOf, NgIf} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {SharedModule} from '../shared/shared.module';
import {HomeComponent} from './pages/home/home.component';
import {NotFoundComponent} from './pages/not-found/not-found.component';
import {ArticleCategoryListComponent} from './components/article-category-list/article-category-list.component';
import {MatTableModule} from '@angular/material/table';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatInputModule} from '@angular/material/input';
import {ArticleCategoryEditorComponent} from './components/article-category-editor/article-category-editor.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {CoreModule} from '../core/core.module';
import {ArticleListComponent} from './components/article-list/article-list.component';
import {ArticleEditorComponent} from './components/article-editor/article-editor.component';

@NgModule({
  declarations: [
    HomeComponent,
    NotFoundComponent,
    ArticleCategoryListComponent,
    ArticleCategoryEditorComponent,
    ArticleListComponent,
    ArticleEditorComponent,
  ],
  imports: [
    SharedModule,
    ReactiveFormsModule,
    RouterLink,
    NgIf,
    AsyncPipe,
    MatButtonModule,
    RouterOutlet,
    NgForOf,
    MatTableModule,
    DatePipe,
    MatTooltipModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    JsonPipe,
    MatSnackBarModule,
    MatIconModule,
    MatMenuModule,
    CoreModule,
  ],
  providers: [],
})
export class FeaturesModule {
}
