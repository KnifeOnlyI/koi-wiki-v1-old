import {Component} from '@angular/core';
import {ArticleService} from '../../../shared/services/article/article.service';
import {ArticleModel} from '../../../shared/models/article/article.model';
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
  selector: 'app-article--list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
  providers: [
    {
      provide: MatPaginatorIntl,
      useClass: PaginatorService,
    },
  ],
})
export class ArticleListComponent {
  /**
   * The data to display.
   */
  data?: PageableModel<ArticleModel>;

  /**
   * The list of columns to display.
   */
  displayedColumns = new Array<string>(
    'id',
    'title',
    'description',
    'createdAt',
    'lastUpdateAt',
    'deletedAt',
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
    q: new FormControl(),
  });

  /**
   * The flag to indicates if the actions bar must be displayed or not.
   */
  readonly showActionsBar: boolean;

  /**
   * The flag to indicates if the button to edit not deleted article must be displayed or not.
   */
  readonly showEditArticleButton: boolean;

  /**
   * The flag to indicates if the button to edit deleted article must be displayed or not.
   */
  readonly showEditDeletedArticleButton: boolean;

  /**
   * The flag to indicates if the button to delete not deleted article must be displayed or not.
   */
  readonly showDeleteArticleButton: boolean;

  /**
   * The flag to indicates if the button to delete deleted article must be displayed or not.
   */
  readonly showDeleteDeletedArticleButton: boolean;

  /**
   * The flag to indicates if the user can search deleted article.
   */
  private readonly canSearchDeletedArticle: boolean;

  /**
   * Create a new instance.
   *
   * @param articleService The service to manage article
   * @param snackbarService The service to manage snackbar
   * @param translateService The service to manage translations
   * @param dialog The service to manage material dialogs
   * @param userService The service to manage users
   */
  constructor(
    private readonly articleService: ArticleService,
    private readonly snackbarService: SnackbarService,
    private readonly translateService: TranslateService,
    private readonly dialog: MatDialog,
    private readonly userService: UserService,
  ) {
    const canReadDeletedArticle = this.userService.hasRole(Role.READ_DELETED_ARTICLE);

    this.showEditArticleButton = this.userService.hasRole(Role.UPDATE_ARTICLE);
    this.showDeleteArticleButton = this.userService.hasRole(Role.DELETE_ARTICLE);
    this.showEditDeletedArticleButton = canReadDeletedArticle && this.userService.hasRole(Role.UPDATED_DELETED_ARTICLE);
    this.showDeleteDeletedArticleButton = canReadDeletedArticle && this.userService.hasRole(Role.DELETE_DELETED_ARTICLE);

    this.showActionsBar = this.userService.hasRole(Role.CREATE_ARTICLE);

    const showActionsColumn = this.showEditArticleButton
      || this.showEditDeletedArticleButton
      || this.showDeleteArticleButton
      || this.showDeleteDeletedArticleButton;

    if (showActionsColumn) {
      this.displayedColumns.push('actions');
    }

    this.canSearchDeletedArticle = canReadDeletedArticle && this.userService.hasRole(Role.SEARCH_DELETED_ARTICLE);

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
    const deleted = !this.canSearchDeletedArticle ? false : undefined;

    this.articleService.search(
      this.page,
      this.pageSize,
      this.sort,
      deleted,
      this.searchForm.controls.q.value,
    ).subscribe((results) => this.data = results);
  }

  /**
   * Delete the entity with the specified ID.
   *
   * @param id The ID of entity to delete
   */
  private delete(id: number): void {
    this.articleService.delete(id).subscribe(() => {
      this.refresh();

      this.snackbarService.open('articles.actions.delete.success');
    });
  }
}
