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
    this.tooltipContent = this.dateService.format(this.date, 'HH:mm:ss');
    this.spanContent = this.dateService.format(this.date, 'YYYY/MM/dd');
  }
}
