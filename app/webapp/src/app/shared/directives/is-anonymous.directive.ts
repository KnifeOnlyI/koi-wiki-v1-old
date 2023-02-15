import {Directive, ElementRef} from '@angular/core';
import {UserService} from '../../core/auth/service/user.service';

/**
 * A directive to check if the user is anonymous.
 */
@Directive({
  selector: '[isAnonymous]',
})
export class IsAnonymousDirective {
  /**
   * Create a new instance.
   *
   * @param elementRef The HTML element ref
   */
  constructor(private elementRef: ElementRef) {
    if (UserService.isAuthenticated) {
      elementRef.nativeElement.remove();
    }
  }

}
