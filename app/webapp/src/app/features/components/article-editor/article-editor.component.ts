import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Objects} from '../../../shared/utils/objects';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ArticleService} from '../../../shared/services/article/article.service';
import {ArticleModel} from '../../../shared/models/article/article.model';
import {SnackbarService} from '../../../shared/services/snackbar/snackbar.service';

@Component({
  selector: 'app-article-editor',
  templateUrl: './article-editor.component.html',
  styleUrls: ['./article-editor.component.scss'],
})
export class ArticleEditorComponent {
  readonly articleId: number | undefined;
  readonly form = new FormGroup({
    title: new FormControl('', {nonNullable: true, validators: [Validators.required, Validators.maxLength(255)]}),
    description: new FormControl('', {
      nonNullable: true,
      validators: [Validators.maxLength(1000)],
    }),
    content: new FormControl('', {
      nonNullable: true,
      validators: [Validators.maxLength(65000)],
    }),
    isArchived: new FormControl(true),
  });

  articleModel?: ArticleModel;

  /**
   * Create a new instance.
   *
   * @param router The service to manage router
   * @param activatedRoute The service to manage the activated route
   * @param articleService The service to manage article.
   * @param snackbarService The service to manage snackbar
   */
  constructor(
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute,
    private readonly articleService: ArticleService,
    private readonly snackbarService: SnackbarService,
  ) {
    const articleId = this.activatedRoute.snapshot.paramMap.get('id');

    if (!Objects.isNull(articleId)) {
      this.articleId = Number(articleId);

      this.articleService.getById(this.articleId).subscribe((article) => {
        this.articleModel = article;

        this.form.reset(this.articleModel);
      });
    }
  }

  /**
   * Executed on form submit operation.
   */
  onSubmit(): void {
    this.articleService.save({
      ...this.form.getRawValue(),
      id: this.articleId,
    }).subscribe(() => {
      this.snackbarService.open(Objects.isNull(this.articleId)
        ? 'articles.actions.create.success'
        : 'articles.actions.edit.success',
      );

      this.router.navigate(['articles']).then();
    });
  }

  /**
   * Executed on form reset operation.
   */
  onReset($event: Event): void {
    if (!Objects.isNull(this.articleModel)) {
      $event.preventDefault();

      this.form.reset(this.articleModel);
    }
  }
}
