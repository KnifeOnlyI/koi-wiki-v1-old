import {Injectable} from "@angular/core";
import {DatePipe} from "@angular/common";

/**
 * The service to manage dates.
 */
@Injectable()
export class DateService {
  /**
   * The date pipe to manage formats.
   */
  private readonly datePipe = new DatePipe('en');

  /**
   * Format the specified date.
   *
   * @param value The date to format
   * @param format The format
   *
   * @return The formatted date
   */
  format(value: Date | null | undefined, format: string): string {
    return this.datePipe.transform(value, format) ?? '';
  }
}
