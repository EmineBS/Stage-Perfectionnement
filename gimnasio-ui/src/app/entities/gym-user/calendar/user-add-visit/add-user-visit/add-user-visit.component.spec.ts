import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserVisitComponent } from './add-user-visit.component';

describe('AddUserVisitComponent', () => {
  let component: AddUserVisitComponent;
  let fixture: ComponentFixture<AddUserVisitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddUserVisitComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AddUserVisitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
