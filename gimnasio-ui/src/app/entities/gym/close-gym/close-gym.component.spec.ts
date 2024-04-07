import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseGymComponent } from './close-gym.component';

describe('CloseGymComponent', () => {
  let component: CloseGymComponent;
  let fixture: ComponentFixture<CloseGymComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CloseGymComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CloseGymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
