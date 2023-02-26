import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BooleanViewerComponent} from './boolean-viewer.component';

describe('BooleanViewerComponent', () => {
  let component: BooleanViewerComponent;
  let fixture: ComponentFixture<BooleanViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BooleanViewerComponent],
    })
      .compileComponents();

    fixture = TestBed.createComponent(BooleanViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
