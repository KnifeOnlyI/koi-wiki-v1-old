import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {PageableModel} from '../../models/pageable.model';
import {Objects} from '../../utils/objects';
import {ArticleModel} from '../../models/article/article.model';

/**
 * The service to manage article.
 */
@Injectable()
export class ArticleService {
  /**
   * The base URL of REST resource to manage articles.
   */
  private readonly baseResourceUrl = `${environment.baseApiUrl}/articles`;

  /**
   * Create a new instance.
   *
   * @param http The service to manage HTTP requests
   */
  constructor(private readonly http: HttpClient) {
  }

  /**
   * Perform a search based on the specified criteria.
   *
   * @param page The page number
   * @param pageSize The page size
   * @param sort The sorting property and direction
   * @param deleted The deleted criteria
   * @param q The query string criteria
   */
  search(
    page?: number | null,
    pageSize?: number | null,
    sort?: string | null,
    deleted?: boolean | null,
    q?: string | null,
  ): Observable<PageableModel<ArticleModel>> {
    const params = {} as any;

    if (!Objects.isNull(page)) {
      params.page = page;
    }

    if (!Objects.isNull(pageSize)) {
      params.pageSize = pageSize;
    }

    if (!Objects.isBlank(sort)) {
      params.sort = sort;
    }

    if (!Objects.isNull(deleted)) {
      params.deleted = deleted;
    }

    if (!Objects.isBlank(q)) {
      params.q = q;
    }

    return this.http.get<PageableModel<ArticleModel>>(
      this.baseResourceUrl,
      {
        params,
      });
  }

  /**
   * Get an entity by ID.
   *
   * @param id The ID.
   *
   * @return An observable on the result
   */
  getById(id: number): Observable<ArticleModel> {
    return this.http.get(`${this.baseResourceUrl}/${id}`);
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
      ? this.http.post(this.baseResourceUrl, data)
      : this.http.put(`${this.baseResourceUrl}/${data.id}`, data);
  }

  /**
   * Delete the specified entity.
   *
   * @param id The ID of entity to delete
   *
   * @return An empty observable
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseResourceUrl}/${id}`);
  }
}
