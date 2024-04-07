import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymBadgesComponent } from './gym-badges.component';

describe('GymBadgesComponent', () => {
  let component: GymBadgesComponent;
  let fixture: ComponentFixture<GymBadgesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymBadgesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GymBadgesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
