import {gql, Mutation} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class DeleteArticleQuery extends Mutation<any> {
  override document = gql`mutation deleteArticle($id: ID!) {
    deleteArticle(id: $id)
  }
  `;
}
