import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TranslateService} from '@ngx-translate/core';

/**
 * The service to manage the snackbar.
 */
@Injectable()
export class SnackbarService {
  /**
   * Create a new instance.
   *
   * @param matSnackbarService The service to manage material snackbars
   * @param translateService The service to manage translations
   */
  constructor(private readonly matSnackbarService: MatSnackBar, private readonly translateService: TranslateService) {
  }

  /**
   * Open the snackbar (replace the previously opened).
   *
   * @param messageKey The translation key of the message
   * @param duration The duration
   * @param closeActionMessageKey The translation key of the close action message
   */
  open(messageKey: string, duration = 2000, closeActionMessageKey = 'general.actions.close'): void {
    this.matSnackbarService.open(
      this.translateService.instant(messageKey),
      this.translateService.instant(closeActionMessageKey), {
        horizontalPosition: 'start',
        duration,
      });
  }
}
