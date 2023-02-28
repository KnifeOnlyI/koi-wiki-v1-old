import {Component, Input, OnInit} from '@angular/core';
import {DateService} from '../../services/date/date.service';

/**
 * The component to show a date and hours.
 */
@Component({
  selector: 'app-date-viewer',
  templateUrl: './date-viewer.component.html',
  styleUrls: ['./date-viewer.component.scss'],
})
export class DateViewerComponent implements OnInit {
  /**
   * The date to format.
   */
  @Input()
  date?: Date | null;

  /**
   * The date format.
   */
  @Input()
  format = 'YYYY/MM/dd';

  /**
   * The format of date in the tooltip.
   */
  @Input()
  tooltipFormat = 'HH:mm:ss';

  /**
   * The tooltip content (hours).
   */
  tooltipContent!: string;

  /**
   * The span content (date without hours).
   */
  spanContent!: string;

  /**
   * Create a new instance.
   *
   * @param dateService The service to manage dates.
   */
  constructor(private readonly dateService: DateService) {
  }

  ngOnInit(): void {
    this.tooltipContent = this.dateService.format(this.date, this.tooltipFormat);
    this.spanContent = this.dateService.format(this.date, this.format);
  }
}
