import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymUsersComponent } from './gym-users.component';

describe('GymUsersComponent', () => {
  let component: GymUsersComponent;
  let fixture: ComponentFixture<GymUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymUsersComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GymUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
