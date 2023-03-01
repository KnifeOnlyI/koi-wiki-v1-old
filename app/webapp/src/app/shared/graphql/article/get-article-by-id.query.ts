import {gql, Query} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class GetArticleByIdQuery extends Query<any> {
  override document = gql`query getArticleById($id: ID!) {
    getArticleById(id: $id) {
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
  }
  `;
}
