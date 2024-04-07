import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavCheckinComponent } from './nav-checkin.component';

describe('NavCheckinComponent', () => {
  let component: NavCheckinComponent;
  let fixture: ComponentFixture<NavCheckinComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavCheckinComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavCheckinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
