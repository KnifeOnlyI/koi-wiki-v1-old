import {gql, Query} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class GetArticleCategoryByIdQuery extends Query<any> {
  override document = gql`query getArticleCategoryById($id: ID!) {
    getArticleCategoryById(id: $id) {
      id
      name
      description
      createdAt
      lastUpdateAt
      deletedAt
    }
  }
  `;
}
