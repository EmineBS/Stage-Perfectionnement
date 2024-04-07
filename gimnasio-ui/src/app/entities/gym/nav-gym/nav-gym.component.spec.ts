import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavGymComponent } from './nav-gym.component';

describe('NavGymComponent', () => {
  let component: NavGymComponent;
  let fixture: ComponentFixture<NavGymComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavGymComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavGymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
