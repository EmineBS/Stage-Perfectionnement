import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeactivateBadgeComponent } from './deactivate-badge.component';

describe('DeactivateBadgeComponent', () => {
  let component: DeactivateBadgeComponent;
  let fixture: ComponentFixture<DeactivateBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeactivateBadgeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DeactivateBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
