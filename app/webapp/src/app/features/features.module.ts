import {NgModule} from '@angular/core';
import {ProfileComponent} from './profile/profile.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HomeComponent} from './home/home.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {RouterLink} from "@angular/router";
import {AsyncPipe, NgIf} from "@angular/common";
import {ArticlesComponent} from "./articles/articles.component";

@NgModule({
  declarations: [
    ProfileComponent,
    HomeComponent,
    NotFoundComponent,
    ArticlesComponent
  ],
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgIf,
    AsyncPipe
  ],
  providers: []
})
export class FeaturesModule {
}
