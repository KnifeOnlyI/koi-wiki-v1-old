import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Objects} from '../../../shared/utils/objects';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ArticleCategoryService} from '../../../shared/services/article/article-category.service';
import {ArticleCategoryModel} from '../../../shared/models/article/article-category.model';
import {SnackbarService} from '../../../shared/services/snackbar/snackbar.service';

@Component({
  selector: 'app-article-category-editor',
  templateUrl: './article-category-editor.component.html',
  styleUrls: ['./article-category-editor.component.scss'],
})
export class ArticleCategoryEditorComponent {
  readonly articleId: number | undefined;
  readonly form = new FormGroup({
    name: new FormControl('', {nonNullable: true, validators: [Validators.required, Validators.maxLength(255)]}),
    description: new FormControl<string>(undefined as unknown as any, {
      nonNullable: true,
      validators: [Validators.maxLength(1000)],
    }),
  });

  articleCategoryModel?: ArticleCategoryModel;

  /**
   * Create a new instance.
   *
   * @param router The service to manage router
   * @param activatedRoute The service to manage the activated route
   * @param articleCategoryService The service to manage article categories.
   * @param snackbarService The service to manage snackbar
   */
  constructor(
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute,
    private readonly articleCategoryService: ArticleCategoryService,
    private readonly snackbarService: SnackbarService,
  ) {
    const articleId = this.activatedRoute.snapshot.paramMap.get('id');

    if (!Objects.isNull(articleId)) {
      this.articleId = Number(articleId);

      this.articleCategoryService.getById(this.articleId).subscribe((articleCategory) => {
        this.articleCategoryModel = articleCategory;

        this.form.reset(this.articleCategoryModel);
      });
    }
  }

  /**
   * Executed on form submit operation.
   */
  onSubmit(): void {
    this.articleCategoryService.save({
      ...this.form.getRawValue(),
      id: this.articleId,
    }).subscribe(() => {
      this.snackbarService.open(Objects.isNull(this.articleId)
        ? 'articles-categories.actions.create.success'
        : 'articles-categories.actions.edit.success',
      );

      this.router.navigate(['articles-categories']).then();
    });
  }

  /**
   * Executed on form reset operation.
   */
  onReset($event: Event): void {
    if (!Objects.isNull(this.articleCategoryModel)) {
      $event.preventDefault();

      this.form.reset(this.articleCategoryModel);
    }
  }
}
