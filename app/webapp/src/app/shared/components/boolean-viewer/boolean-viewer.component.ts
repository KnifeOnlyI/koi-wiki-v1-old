import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-boolean-viewer',
  templateUrl: './boolean-viewer.component.html',
  styleUrls: ['./boolean-viewer.component.scss'],
})
export class BooleanViewerComponent {
  @Input()
  value?: boolean | null;
}
