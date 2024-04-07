import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgeCalendarComponent } from './badge-calendar.component';

describe('BadgeCalendarComponent', () => {
  let component: BadgeCalendarComponent;
  let fixture: ComponentFixture<BadgeCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BadgeCalendarComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BadgeCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
