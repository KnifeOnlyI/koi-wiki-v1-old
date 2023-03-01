import {gql, Mutation} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class DeleteArticleCategoryQuery extends Mutation<any> {
  override document = gql`mutation deleteArticleCategory($id: ID!) {
    deleteArticleCategory(id: $id)
  }
  `;
}
