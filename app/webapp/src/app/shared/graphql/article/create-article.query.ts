import {gql, Mutation} from 'apollo-angular';
import {Injectable} from '@angular/core';

@Injectable()
export class CreateArticleQuery extends Mutation<any> {
  override document = gql`mutation createArticle($data: CreateArticle!) {
    createArticle(data: $data) {
      id
    }
  }
  `;
}
