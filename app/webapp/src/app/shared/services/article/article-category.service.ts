import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ArticleCategoryModel} from '../../models/article/article-category.model';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {PageableModel} from '../../models/pageable.model';
import {Objects} from '../../utils/objects';

/**
 * The service to manage article categories.
 */
@Injectable()
export class ArticleCategoryService {
  /**
   * The base URL of REST resource to manage articles categories.
   */
  private readonly baseResourceUrl = `${environment.baseApiUrl}/articles-categories`;

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
   * @param name The name criteria
   * @param description The description criteria
   * @param excludedIds The excluded ids
   */
  search(
    page?: number | null,
    pageSize?: number | null,
    sort?: string | null,
    deleted?: boolean | null,
    name?: string | null,
    description?: string | null,
    excludedIds = new Array<number>(),
  ): Observable<PageableModel<ArticleCategoryModel>> {
    const params = {
      excludedIds,
    } as any;

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

    if (!Objects.isBlank(name)) {
      params.name = name;
    }

    if (!Objects.isBlank(description)) {
      params.description = description;
    }

    return this.http.get<PageableModel<ArticleCategoryModel>>(
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
  getById(id: number): Observable<ArticleCategoryModel> {
    return this.http.get(`${this.baseResourceUrl}/${id}`);
  }

  /**
   * Save the specified data.
   *
   * @param data The data to save
   *
   * @return An observable on the result that contains the saved data
   */
  save(data: ArticleCategoryModel): Observable<ArticleCategoryModel> {
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
