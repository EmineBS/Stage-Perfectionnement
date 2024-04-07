import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymAmineComponent } from './gym-amine.component';

describe('GymAmineComponent', () => {
  let component: GymAmineComponent;
  let fixture: ComponentFixture<GymAmineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GymAmineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GymAmineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
