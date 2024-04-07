import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavCriteriasComponent } from './nav-criterias.component';

describe('NavCriteriasComponent', () => {
  let component: NavCriteriasComponent;
  let fixture: ComponentFixture<NavCriteriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavCriteriasComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavCriteriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
