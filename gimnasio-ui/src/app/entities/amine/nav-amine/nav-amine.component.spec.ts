import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavAmineComponent } from './nav-amine.component';

describe('NavAmineComponent', () => {
  let component: NavAmineComponent;
  let fixture: ComponentFixture<NavAmineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavAmineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavAmineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
