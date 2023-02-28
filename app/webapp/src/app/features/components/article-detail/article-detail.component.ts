import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Objects} from '../../../shared/utils/objects';
import {ArticleService} from '../../../shared/services/article/article.service';
import {ArticleModel} from '../../../shared/models/article/article.model';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent {
  readonly articleId: number | undefined;
  article?: ArticleModel;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly articleService: ArticleService,
  ) {
    const articleId = this.activatedRoute.snapshot.paramMap.get('id');

    if (!Objects.isNull(articleId)) {
      this.articleId = Number(articleId);

      this.articleService.getById(this.articleId).subscribe((article) => {
        this.article = article;
      });
    }
  }
}
