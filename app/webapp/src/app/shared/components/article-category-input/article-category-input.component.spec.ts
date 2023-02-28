import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ArticleCategoryInputComponent} from './article-category-input.component';

describe('ArticleCategoryInputComponent', () => {
  let component: ArticleCategoryInputComponent;
  let fixture: ComponentFixture<ArticleCategoryInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ArticleCategoryInputComponent],
    })
      .compileComponents();

    fixture = TestBed.createComponent(ArticleCategoryInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
