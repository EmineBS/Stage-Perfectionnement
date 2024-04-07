import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavCriteriaComponent } from './nav-criteria.component';

describe('NavCriteriaComponent', () => {
  let component: NavCriteriaComponent;
  let fixture: ComponentFixture<NavCriteriaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavCriteriaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavCriteriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
