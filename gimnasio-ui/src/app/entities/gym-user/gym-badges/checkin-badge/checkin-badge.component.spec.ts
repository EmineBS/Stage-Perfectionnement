import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckinBadgeComponent } from './checkin-badge.component';

describe('CheckinBadgeComponent', () => {
  let component: CheckinBadgeComponent;
  let fixture: ComponentFixture<CheckinBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CheckinBadgeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckinBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
