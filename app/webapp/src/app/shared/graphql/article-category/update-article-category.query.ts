import {gql, Mutation} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class UpdateArticleCategoryQuery extends Mutation<any> {
  override document = gql`mutation updateArticleCategory($data: UpdateArticleCategory!) {
    updateArticleCategory(data: $data) {
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
