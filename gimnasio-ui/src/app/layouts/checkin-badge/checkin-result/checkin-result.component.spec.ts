import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckinResultComponent } from './checkin-result.component';

describe('CheckinResultComponent', () => {
  let component: CheckinResultComponent;
  let fixture: ComponentFixture<CheckinResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CheckinResultComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckinResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
