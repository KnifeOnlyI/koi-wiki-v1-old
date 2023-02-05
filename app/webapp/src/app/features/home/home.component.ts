import {Component, OnInit} from '@angular/core';
import {UserService} from "../../core/auth/service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  isAuthenticated?: boolean;
  username?: string | null;

  constructor(private readonly userService: UserService, private readonly router: Router) {
  }

  async ngOnInit(): Promise<void> {
    this.isAuthenticated = await this.userService.isAuthenticated();

    this.username = await this.userService.getUsername();
  }

  async login() {
    if (!this.isAuthenticated) {
      await this.userService.login(window.location.origin + this.router.routerState.snapshot.url);
    }
  }

  async logout() {
    if (this.isAuthenticated) {
      await this.userService.logout();
    }
  }
}
