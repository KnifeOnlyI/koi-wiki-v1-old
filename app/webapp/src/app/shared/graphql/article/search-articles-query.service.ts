import {gql, Query} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class SearchArticlesQuery extends Query<any> {
  override document = gql`query searchArticles($criteria: ArticleSearchCriteria!) {
    searchArticles(criteria: $criteria) {
      content {
        id
        title
        description
        content
        isArchived
        author {
          id
          username
        }
        categories {
          id
          name
        }
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
