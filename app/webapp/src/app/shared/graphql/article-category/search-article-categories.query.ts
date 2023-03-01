import {gql, Query} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class SearchArticleCategoriesQuery extends Query<any> {
  override document = gql`query searchArticleCategories($criteria: ArticleCategorySearchCriteria!) {
    searchArticleCategories(criteria: $criteria) {
      content {
        id
        name
        description
        createdAt
        lastUpdateAt
        deletedAt
      }
      number
      totalPages
      totalElements
    }
  }
  `;
}
