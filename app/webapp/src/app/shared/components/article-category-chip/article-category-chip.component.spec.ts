import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ArticleCategoryChipComponent} from './article-category-chip.component';

describe('ArticleCategoryChipComponent', () => {
  let component: ArticleCategoryChipComponent;
  let fixture: ComponentFixture<ArticleCategoryChipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ArticleCategoryChipComponent],
    })
      .compileComponents();

    fixture = TestBed.createComponent(ArticleCategoryChipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
