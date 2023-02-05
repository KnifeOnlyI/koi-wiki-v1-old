import {Component} from '@angular/core';
import {UserService} from "../../core/auth/service/user.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-home',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent {
  canCreateArticle = this.userService.hasRole(['create-article']);
  canDeleteArticle = this.userService.hasRole(['delete-article']);

  constructor(private readonly userService: UserService, private readonly http: HttpClient) {
  }

  createArticle() {
    this.http.get(`${environment.baseApiUrl}/articles/create`).subscribe(res => {
      console.debug('CREATED ARTICLE', res);
    });
  }

  deleteArticle() {
    this.http.get(`${environment.baseApiUrl}/articles/delete`).subscribe(res => {
      console.debug('DELETED ARTICLE', res);
    });
  }


}
