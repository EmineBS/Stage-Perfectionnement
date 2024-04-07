import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavPacksComponent } from './nav-packs.component';

describe('NavPacksComponent', () => {
  let component: NavPacksComponent;
  let fixture: ComponentFixture<NavPacksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavPacksComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavPacksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
