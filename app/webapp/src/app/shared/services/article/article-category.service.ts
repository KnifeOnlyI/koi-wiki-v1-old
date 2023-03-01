import {Injectable} from '@angular/core';
import {map, Observable} from 'rxjs';
import {ArticleCategoryModel} from '../../models/article/article-category.model';
import {PageableModel} from '../../models/pageable.model';
import {Objects} from '../../utils/objects';
import {SearchArticleCategoriesQuery} from '../../graphql/article-category/search-article-categories.query';
import {GetArticleCategoryByIdQuery} from '../../graphql/article-category/get-article-category-by-id.query';
import {DeleteArticleCategoryQuery} from '../../graphql/article-category/delete-article-category.query';
import {CreateArticleCategoryQuery} from '../../graphql/article-category/create-article-category.query';
import {UpdateArticleCategoryQuery} from '../../graphql/article-category/update-article-category.query';

/**
 * The service to manage article categories.
 */
@Injectable()
export class ArticleCategoryService {

  /**
   * Create a new instance.
   *
   * @param searchArticleCategoriesQuery The query to search article categories
   * @param getArticleCategoryByIdQuery
   * @param deleteArticleCategoryQuery
   * @param createArticleCategoryQuery
   * @param updateArticleCategoryQuery
   */
  constructor(
    private readonly searchArticleCategoriesQuery: SearchArticleCategoriesQuery,
    private readonly getArticleCategoryByIdQuery: GetArticleCategoryByIdQuery,
    private readonly deleteArticleCategoryQuery: DeleteArticleCategoryQuery,
    private readonly createArticleCategoryQuery: CreateArticleCategoryQuery,
    private readonly updateArticleCategoryQuery: UpdateArticleCategoryQuery,
  ) {
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
    return this.searchArticleCategoriesQuery.fetch({
      criteria: {
        page,
        pageSize,
        sort,
        deleted,
        name,
        description,
      },
    }).pipe(map(res => res.data.searchArticleCategories));
  }

  /**
   * Get an entity by ID.
   *
   * @param id The ID.
   *
   * @return An observable on the result
   */
  getById(id: number): Observable<ArticleCategoryModel> {
    return this.getArticleCategoryByIdQuery.fetch({
      id,
    }).pipe(map(res => res.data.getArticleCategoryById));
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
      ? this.createArticleCategoryQuery.mutate({data}).pipe(map(res => res.data.createArticleCategory))
      : this.updateArticleCategoryQuery.mutate({data}).pipe(map(res => res.data.updateArticleCategory));
  }

  /**
   * Delete the specified entity.
   *
   * @param id The ID of entity to delete
   *
   * @return An empty observable
   */
  delete(id: number): Observable<any> {
    return this.deleteArticleCategoryQuery.mutate({id});
  }
}
