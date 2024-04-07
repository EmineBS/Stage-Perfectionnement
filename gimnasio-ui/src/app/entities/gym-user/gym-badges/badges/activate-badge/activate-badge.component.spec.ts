import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivateBadgeComponent } from './activate-badge.component';

describe('ActivateBadgeComponent', () => {
  let component: ActivateBadgeComponent;
  let fixture: ComponentFixture<ActivateBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivateBadgeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ActivateBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
