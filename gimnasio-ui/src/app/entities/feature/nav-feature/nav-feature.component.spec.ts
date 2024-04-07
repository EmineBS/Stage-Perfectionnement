import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavFeatureComponent } from './nav-feature.component';

describe('NavFeatureComponent', () => {
  let component: NavFeatureComponent;
  let fixture: ComponentFixture<NavFeatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavFeatureComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavFeatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
