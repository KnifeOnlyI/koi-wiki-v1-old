import {debounce, timer} from 'rxjs';

/**
 * Contains all forms constants.
 */
export class FormsConstants {
  static readonly DEBOUNCE = debounce(() => timer(150));
}
