import {gql, Mutation} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class UpdateArticleQuery extends Mutation<any> {
  override document = gql`mutation updateArticle($data: UpdateArticle!) {
    updateArticle(data: $data) {
      id
    }
  }
  `;
}
