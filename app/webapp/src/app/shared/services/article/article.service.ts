import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {PageableModel} from '../../models/pageable.model';
import {Objects} from '../../utils/objects';
import {ArticleModel} from '../../models/article/article.model';
import {SearchArticlesQuery} from '../../graphql/article/search-articles-query.service';
import {GetArticleByIdQuery} from '../../graphql/article/get-article-by-id.query';
import {DeleteArticleQuery} from '../../graphql/article/delete-article.query';
import {CreateArticleQuery} from '../../graphql/article/create-article.query';
import {UpdateArticleQuery} from '../../graphql/article/update-article.query';

/**
 * The service to manage article.
 */
@Injectable()
export class ArticleService {

  /**
   * Create a new instance.
   */
  constructor(
    private readonly searchArticlesQuery: SearchArticlesQuery,
    private readonly getArticleByIdQuery: GetArticleByIdQuery,
    private readonly deleteArticleQuery: DeleteArticleQuery,
    private readonly createArticleQuery: CreateArticleQuery,
    private readonly updateArticleQuery: UpdateArticleQuery,
  ) {
  }

  /**
   * Perform a search based on the specified criteria.
   *
   * @param page The page number
   * @param pageSize The page size
   * @param sort The sorting property and direction
   * @param deleted The deleted criteria
   * @param archived The archived criteria
   * @param authorId The ID of author
   * @param q The query string criteria
   */
  search(
    page?: number | null,
    pageSize?: number | null,
    sort?: string | null,
    deleted?: boolean | null,
    archived?: boolean | null,
    authorId?: string | null,
    q?: string | null,
  ): Observable<PageableModel<ArticleModel>> {
    return this.searchArticlesQuery.fetch({
      criteria: {
        page,
        pageSize,
        sort,
        deleted,
        archived,
        authorId,
        q,
      },
    }).pipe(map(res => res.data.searchArticles));
  }

  /**
   * Get an entity by ID.
   *
   * @param id The ID.
   *
   * @return An observable on the result
   */
  getById(id: number): Observable<ArticleModel> {
    return this.getArticleByIdQuery.fetch({
      id,
    }).pipe(map(res => res.data.getArticleById));
  }

  /**
   * Save the specified data.
   *
   * @param data The data to save
   *
   * @return An observable on the result that contains the saved data
   */
  save(data: ArticleModel): Observable<ArticleModel> {
    return Objects.isNull(data.id)
      ? this.createArticleQuery.mutate({data}).pipe(map(res => res.data.createArticle))
      : this.updateArticleQuery.mutate({data}).pipe(map(res => res.data.updateArticle));
  }

  /**
   * Delete the specified entity.
   *
   * @param id The ID of entity to delete
   *
   * @return An empty observable
   */
  delete(id: number): Observable<any> {
    return this.deleteArticleQuery.mutate({id});
  }
}
