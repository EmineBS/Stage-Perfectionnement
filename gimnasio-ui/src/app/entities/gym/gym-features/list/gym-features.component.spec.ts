import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymFeautersComponent } from './gym-features.component';

describe('GymFeautersComponent', () => {
  let component: GymFeautersComponent;
  let fixture: ComponentFixture<GymFeautersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymFeautersComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GymFeautersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
