import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-article-category-chip',
  templateUrl: './article-category-chip.component.html',
  styleUrls: ['./article-category-chip.component.scss'],
})
export class ArticleCategoryChipComponent {
  @Input()
  id!: number;


}
