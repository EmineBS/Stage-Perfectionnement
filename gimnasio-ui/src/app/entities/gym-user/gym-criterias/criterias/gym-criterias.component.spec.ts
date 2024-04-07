import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymCriteriasComponent } from './gym-criterias.component';

describe('GymCriteriasComponent', () => {
  let component: GymCriteriasComponent;
  let fixture: ComponentFixture<GymCriteriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymCriteriasComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GymCriteriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
