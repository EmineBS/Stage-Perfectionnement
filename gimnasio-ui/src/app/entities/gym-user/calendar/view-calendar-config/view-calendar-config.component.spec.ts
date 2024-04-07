import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCalendarConfigComponent } from './view-calendar-config.component';

describe('ViewCalendarConfigComponent', () => {
  let component: ViewCalendarConfigComponent;
  let fixture: ComponentFixture<ViewCalendarConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewCalendarConfigComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewCalendarConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
