import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavBadgesComponent } from './nav-badges.component';

describe('NavBadgesComponent', () => {
  let component: NavBadgesComponent;
  let fixture: ComponentFixture<NavBadgesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavBadgesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavBadgesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
