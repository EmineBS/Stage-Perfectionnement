import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymFeaturesComponent } from './gym-features.component';

describe('GymFeaturesComponent', () => {
  let component: GymFeaturesComponent;
  let fixture: ComponentFixture<GymFeaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymFeaturesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GymFeaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
