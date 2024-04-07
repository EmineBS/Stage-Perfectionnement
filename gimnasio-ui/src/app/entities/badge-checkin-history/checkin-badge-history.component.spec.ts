import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckinBadgeHistoryComponent } from './checkin-badge-history.component';

describe('CheckinBadgeComponent', () => {
  let component: CheckinBadgeHistoryComponent;
  let fixture: ComponentFixture<CheckinBadgeHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CheckinBadgeHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckinBadgeHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
