import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavFeaturesComponent } from './nav-features.component';

describe('NavFeaturesComponent', () => {
  let component: NavFeaturesComponent;
  let fixture: ComponentFixture<NavFeaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavFeaturesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavFeaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
