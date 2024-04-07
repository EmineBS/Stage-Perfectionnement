import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavStoreComponent } from './nav-store.component';

describe('NavStoreComponent', () => {
  let component: NavStoreComponent;
  let fixture: ComponentFixture<NavStoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavStoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavStoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
