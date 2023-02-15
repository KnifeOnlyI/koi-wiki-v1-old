import {Directive, ElementRef} from '@angular/core';
import {UserService} from '../../core/auth/service/user.service';

/**
 * A directive to check if the user is authenticated.
 */
@Directive({
  selector: '[isAuthenticated]',
})
export class IsAuthenticatedDirective {
  /**
   * Create a new instance.
   *
   * @param elementRef The HTML element ref
   */
  constructor(private elementRef: ElementRef) {
    if (!UserService.isAuthenticated) {
      elementRef.nativeElement.remove();
    }
  }

}
