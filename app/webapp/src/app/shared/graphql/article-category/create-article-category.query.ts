import {gql, Mutation} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class CreateArticleCategoryQuery extends Mutation<any> {
  override document = gql`mutation createArticleCategory($data: CreateArticleCategory!) {
    createArticleCategory(data: $data) {
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
