import {Pipe, PipeTransform} from '@angular/core';
import {ValidationErrors} from '@angular/forms';
import {Objects} from '../utils/objects';
import {TranslateService} from '@ngx-translate/core';

/**
 * A pipe to fetch the first reactive form error of a field and return the translated correspondiion
 */
@Pipe({name: 'TranslateFirstError'})
export class TranslateFirstErrorPipe implements PipeTransform {
  /**
   * Create a new instance.
   *
   * @param translateService The service to manage translations
   */
  constructor(private readonly translateService: TranslateService) {
  }

  /**
   * Fetch the first reactive form error in the specified errors and return the translation.
   *
   * @param errors The errors to check
   * @param rootErrorKey The root translation error key
   *
   * @return The corresponding translation
   */
  transform(errors: ValidationErrors | null, rootErrorKey: string): string {
    return Objects.isNull(errors)
      ? ''
      : this.translateService.instant(`${rootErrorKey}.${Object.keys(errors!)[0]}`);
  }

}
