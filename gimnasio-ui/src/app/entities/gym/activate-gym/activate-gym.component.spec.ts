import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivateGymComponent } from './activate-gym.component';

describe('ActivateGymComponent', () => {
  let component: ActivateGymComponent;
  let fixture: ComponentFixture<ActivateGymComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivateGymComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ActivateGymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
