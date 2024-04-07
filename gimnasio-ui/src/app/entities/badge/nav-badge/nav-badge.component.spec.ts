import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavBadgeComponent } from './nav-badge.component';

describe('NavBadgeComponent', () => {
  let component: NavBadgeComponent;
  let fixture: ComponentFixture<NavBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavBadgeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
