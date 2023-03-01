import {NgModule} from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';
import {translateConfiguration} from './config/translate.config';
import {IsAuthenticatedDirective} from './directives/is-authenticated.directive';
import {IsAnonymousDirective} from './directives/is-anonymous.directive';
import {ArticleCategoryService} from './services/article/article-category.service';
import {DateViewerComponent} from './components/date-viewer/date-viewer.component';
import {DateService} from './services/date/date.service';
import {MatTooltipModule} from '@angular/material/tooltip';
import {ErrorStateMatcher, ShowOnDirtyErrorStateMatcher} from '@angular/material/core';
import {TranslateFirstErrorPipe} from './pipes/first-form-error-key.pipe';
import {ConfirmDialogComponent} from './components/confirm-dialog/confirm-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {SnackbarService} from './services/snackbar/snackbar.service';
import {ArticleService} from './services/article/article.service';
import {AsyncPipe, JsonPipe, NgForOf, NgIf} from '@angular/common';
import {BooleanViewerComponent} from './components/boolean-viewer/boolean-viewer.component';
import {ArticleCategoryInputComponent} from './components/article-category-input/article-category-input.component';
import {ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatIconModule} from '@angular/material/icon';
import {ArticleCategoryChipComponent} from './components/article-category-chip/article-category-chip.component';
import {MatChipsModule} from '@angular/material/chips';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {SearchArticleCategoriesQuery} from './graphql/article-category/search-article-categories.query';
import {GetArticleCategoryByIdQuery} from './graphql/article-category/get-article-category-by-id.query';
import {DeleteArticleCategoryQuery} from './graphql/article-category/delete-article-category.query';
import {CreateArticleCategoryQuery} from './graphql/article-category/create-article-category.query';
import {UpdateArticleCategoryQuery} from './graphql/article-category/update-article-category.query';
import {SearchArticlesQuery} from './graphql/article/search-articles-query.service';
import {GetArticleByIdQuery} from './graphql/article/get-article-by-id.query';
import {DeleteArticleQuery} from './graphql/article/delete-article.query';
import {CreateArticleQuery} from './graphql/article/create-article.query';
import {UpdateArticleQuery} from './graphql/article/update-article.query';

@NgModule({
  declarations: [
    IsAuthenticatedDirective,
    IsAnonymousDirective,
    DateViewerComponent,
    TranslateFirstErrorPipe,
    ConfirmDialogComponent,
    BooleanViewerComponent,
    ArticleCategoryInputComponent,
    ArticleCategoryChipComponent,
  ],
  imports: [
    TranslateModule.forRoot(translateConfiguration),
    MatTooltipModule,
    MatDialogModule,
    MatButtonModule,
    AsyncPipe,
    NgIf,
    JsonPipe,
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    NgForOf,
    MatCheckboxModule,
    MatIconModule,
    MatChipsModule,
    MatAutocompleteModule,
  ],
  exports: [
    TranslateModule,
    IsAuthenticatedDirective,
    IsAnonymousDirective,
    DateViewerComponent,
    TranslateFirstErrorPipe,
    BooleanViewerComponent,
    ArticleCategoryInputComponent,
  ],
  providers: [
    ArticleCategoryService,
    ArticleService,
    DateService,
    SnackbarService,
    SearchArticleCategoriesQuery,
    GetArticleCategoryByIdQuery,
    DeleteArticleCategoryQuery,
    CreateArticleCategoryQuery,
    UpdateArticleCategoryQuery,
    SearchArticlesQuery,
    GetArticleByIdQuery,
    DeleteArticleQuery,
    CreateArticleQuery,
    UpdateArticleQuery,
    {
      provide: ErrorStateMatcher,
      useClass: ShowOnDirtyErrorStateMatcher,
    },
  ],
})
export class SharedModule {
}
