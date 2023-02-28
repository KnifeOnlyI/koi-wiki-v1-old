import {Component, ElementRef, forwardRef, Input, ViewChild} from '@angular/core';
import {ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR} from '@angular/forms';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {ArticleCategoryService} from '../../services/article/article-category.service';
import {map, Observable, zip} from 'rxjs';
import {ArticleCategoryModel} from '../../models/article/article-category.model';
import {FormsConstants} from '../../constants/forms.constants';

@Component({
  selector: 'app-article-category-input',
  templateUrl: './article-category-input.component.html',
  styleUrls: ['./article-category-input.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ArticleCategoryInputComponent),
      multi: true,
    },
  ],
})
export class ArticleCategoryInputComponent implements ControlValueAccessor {
  @Input()
  label?: string;

  @ViewChild('searchInput')
  inputRef!: ElementRef<HTMLInputElement>;

  inputForm = new FormControl('', {nonNullable: true});

  selectableValues?: Observable<Array<ArticleCategoryModel>>;

  selectedValues = new Array<ArticleCategoryModel>();

  constructor(public readonly articleCategoryService: ArticleCategoryService) {
    this.inputForm.valueChanges
      .pipe(FormsConstants.DEBOUNCE)
      .subscribe((newValue: any) => this.refreshSelectableValues(newValue));
  }

  registerOnChange(fn: (value: Array<number>) => any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  writeValue(ids?: Array<number> | null): void {
    this.selectedValues = new Array<ArticleCategoryModel>();

    const obs = new Array<Observable<ArticleCategoryModel>>();

    ids?.forEach(id => obs.push(this.articleCategoryService.getById(id)));

    zip(obs).subscribe((categories) => {
      categories.forEach(category => this.selectedValues.push({
        id: category.id,
        name: category.name,
      }));
    });
  }

  remove(selectedValue: ArticleCategoryModel) {
    const alreadySelectedValueIndex = this.selectedValues.findIndex(sv => sv.id === selectedValue.id);

    if (alreadySelectedValueIndex >= 0) {
      this.selectedValues.splice(alreadySelectedValueIndex, 1);

      this.onChange(this.getSelectedIds());
      this.onTouched();
    }
  }

  selected($event: MatAutocompleteSelectedEvent) {
    const selectedValue: ArticleCategoryModel = $event.option.value;
    const alreadySelectedValueIndex = this.selectedValues.findIndex(sv => sv.id === selectedValue.id);

    if (alreadySelectedValueIndex < 0) {
      this.selectedValues.push($event.option.value);
      this.inputRef.nativeElement.value = '';
      this.inputForm.setValue('');

      this.onChange(this.getSelectedIds());
      this.onTouched();
    }
  }

  refreshSelectableValues(query: any): void {
    this.selectableValues = this.articleCategoryService.search(
      0,
      15,
      'name:asc',
      false,
      query?.name ?? query,
      undefined,
      this.getSelectedIds(),
    ).pipe(map((pageResults) => pageResults.content));
  }

  private onChange = (value: Array<number>) => {
  };

  private onTouched = () => {
  };

  private getSelectedIds(): Array<number> {
    return this.selectedValues.map(selectedValue => selectedValue.id!);
  }
}
