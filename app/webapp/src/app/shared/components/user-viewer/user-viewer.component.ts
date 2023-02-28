import {Component, Input, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {UsernameModel} from '../../../core/auth/models/username.model';
import {UserService} from '../../../core/auth/service/user.service';
import {Objects} from '../../utils/objects';

@Component({
  selector: 'app-user-viewer',
  templateUrl: './user-viewer.component.html',
  styleUrls: ['./user-viewer.component.scss'],
})
export class UserViewerComponent implements OnInit {
  /**
   * The ID of user to display.
   */
  @Input()
  userId?: string | null;

  /**
   * The flag to indicates if the user ID must be show in a tooltip on hover.
   */
  @Input()
  userIdTooltip = false;

  /**
   * The span content (date without hours).
   */
  username!: Observable<UsernameModel | null>;

  /**
   * Create a new instance.
   *
   * @param userService The service to manage users.
   */
  constructor(private readonly userService: UserService) {
  }

  ngOnInit(): void {
    this.username = Objects.isBlank(this.userId) ? of({}) : this.userService.getUsernameFromId(this.userId!);
  }


}
