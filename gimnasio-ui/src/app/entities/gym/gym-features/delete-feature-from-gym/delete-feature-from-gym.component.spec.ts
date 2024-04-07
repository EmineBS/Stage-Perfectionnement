import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteUserFromGymComponent } from './delete-feature-from-gym.component';

describe('DeleteUserFromGymComponent', () => {
  let component: DeleteUserFromGymComponent;
  let fixture: ComponentFixture<DeleteUserFromGymComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteUserFromGymComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DeleteUserFromGymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
