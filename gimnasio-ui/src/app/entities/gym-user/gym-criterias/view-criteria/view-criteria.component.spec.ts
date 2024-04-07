import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCriteriaComponent } from './view-criteria.component';

describe('ViewCriteriaComponent', () => {
  let component: ViewCriteriaComponent;
  let fixture: ComponentFixture<ViewCriteriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewCriteriaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewCriteriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
