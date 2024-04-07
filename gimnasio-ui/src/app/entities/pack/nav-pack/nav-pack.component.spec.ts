import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavPackComponent } from './nav-pack.component';

describe('NavPackComponent', () => {
  let component: NavPackComponent;
  let fixture: ComponentFixture<NavPackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavPackComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavPackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
