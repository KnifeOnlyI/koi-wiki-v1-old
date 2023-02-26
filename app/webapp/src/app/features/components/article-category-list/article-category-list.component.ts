import {Component} from '@angular/core';
import {ArticleCategoryService} from '../../../shared/services/article/article-category.service';
import {ArticleCategoryModel} from '../../../shared/models/article/article-category.model';
import {PageableModel} from '../../../shared/models/pageable.model';
import {MatPaginatorIntl, PageEvent} from '@angular/material/paginator';
import {PaginatorService} from '../../../shared/services/paginator/paginator.service';
import {Sort} from '@angular/material/sort';
import {FormControl, FormGroup} from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogComponent} from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import {UserService} from '../../../core/auth/service/user.service';
import {Role} from '../../../core/auth/constants/role';
import {FormsConstants} from '../../../shared/constants/forms.constants';
import {SnackbarService} from '../../../shared/services/snackbar/snackbar.service';

@Component({
  selector: 'app-article-category-list',
  templateUrl: './article-category-list.component.html',
  styleUrls: ['./article-category-list.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useClass: PaginatorService,
    },
  ],
})
export class ArticleCategoryListComponent {
  /**
   * The data to display.
   */
  data?: PageableModel<ArticleCategoryModel>;

  /**
   * The list of columns to display.
   */
  displayedColumns = new Array<string>(
    'id',
    'name',
    'description',
    'createdAt',
    'lastUpdateAt',
    'deletedAt',
  );

  /**
   * The list of filter columns to display.
   */
  displayedFilterColumns = new Array<string>(
    'idFilter',
    'nameFilter',
    'descriptionFilter',
    'createdAtFilter',
    'lastUpdateAtFilter',
    'deletedAtFilter',
  );

  /**
   * The current page number.
   */
  page = 0;

  /**
   * The current page size.
   */
  pageSize = 10;

  /**
   * The current sorting property and direction.
   */
  sort: string | null = 'id:asc';

  /**
   * The search form.
   */
  searchForm = new FormGroup({
    name: new FormControl(),
    description: new FormControl(),
  });

  /**
   * The flag to indicates if the actions bar must be displayed or not.
   */
  readonly showActionsBar: boolean;

  /**
   * The flag to indicates if the button to edit not deleted article must be displayed or not.
   */
  readonly showEditArticleCategoryButton: boolean;

  /**
   * The flag to indicates if the button to edit deleted article must be displayed or not.
   */
  readonly showEditDeletedArticleCategoryButton: boolean;

  /**
   * The flag to indicates if the button to delete not deleted article must be displayed or not.
   */
  readonly showDeleteArticleCategoryButton: boolean;

  /**
   * The flag to indicates if the button to delete deleted article must be displayed or not.
   */
  readonly showDeleteDeletedArticleCategoryButton: boolean;

  /**
   * The flag to indicates if the user can search deleted article categories.
   */
  private readonly canSearchDeletedArticleCategory: boolean;

  /**
   * Create a new instance.
   *
   * @param articleCategoryService The service to manage article category
   * @param snackbarService The service to manage snackbar
   * @param translateService The service to manage translations
   * @param dialog The service to manage material dialogs
   * @param userService The service to manage users
   */
  constructor(
    private readonly articleCategoryService: ArticleCategoryService,
    private readonly snackbarService: SnackbarService,
    private readonly translateService: TranslateService,
    private readonly dialog: MatDialog,
    private readonly userService: UserService,
  ) {
    const canReadDeletedArticleCategory = this.userService.hasRole(Role.READ_DELETED_ARTICLE_CATEGORY);

    this.showEditArticleCategoryButton = this.userService.hasRole(Role.UPDATE_ARTICLE_CATEGORY);
    this.showDeleteArticleCategoryButton = this.userService.hasRole(Role.DELETE_ARTICLE_CATEGORY);
    this.showEditDeletedArticleCategoryButton = canReadDeletedArticleCategory && this.userService.hasRole(Role.UPDATE_DELETED_ARTICLE_CATEGORY);
    this.showDeleteDeletedArticleCategoryButton = canReadDeletedArticleCategory && this.userService.hasRole(Role.DELETE_DELETED_ARTICLE_CATEGORY);

    this.showActionsBar = this.userService.hasRole(Role.CREATE_ARTICLE_CATEGORY);

    const showActionsColumn = this.showEditArticleCategoryButton
      || this.showEditDeletedArticleCategoryButton
      || this.showDeleteArticleCategoryButton
      || this.showDeleteDeletedArticleCategoryButton;

    if (showActionsColumn) {
      this.displayedColumns.push('actions');
      this.displayedFilterColumns.push('actionsFilter');
    }

    this.canSearchDeletedArticleCategory = canReadDeletedArticleCategory && this.userService.hasRole(Role.SEARCH_DELETED_ARTICLE_CATEGORY);

    this.refresh();

    this.searchForm.valueChanges.pipe(FormsConstants.DEBOUNCE).subscribe(this.refresh.bind(this));
  }

  /**
   * Executed on pagination changes.
   *
   * @param $event The pagination change event
   */
  onPaginationChange($event: PageEvent) {
    this.page = $event.pageIndex;
    this.pageSize = $event.pageSize;

    this.refresh();
  }

  /**
   * Executed on sorting changes.
   *
   * @param $event The sorting change event
   */
  onSortChange($event: Sort): void {
    this.sort = !$event.direction ? null : `${$event.active}:${$event.direction}`;

    this.refresh();
  }

  /**
   * Executed on delete operation.
   *
   * @param id The ID of entity to delete
   */
  onDelete(id: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete(id);
      }
    });
  }

  /**
   * Refresh the search results.
   */
  private refresh(): void {
    const deleted = !this.canSearchDeletedArticleCategory ? false : undefined;

    this.articleCategoryService.search(
      this.page,
      this.pageSize,
      this.sort,
      deleted,
      this.searchForm.controls.name.value,
      this.searchForm.controls.description.value,
    ).subscribe((results) => this.data = results);
  }

  /**
   * Delete the entity with the specified ID.
   *
   * @param id The ID of entity to delete
   */
  private delete(id: number): void {
    this.articleCategoryService.delete(id).subscribe(() => {
      this.refresh();

      this.snackbarService.open('articles-categories.actions.delete.success');
    });
  }
}
