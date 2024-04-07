import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderDrawerComponent } from './header-drawer.component';

describe('HeaderDrawerComponent', () => {
  let component: HeaderDrawerComponent;
  let fixture: ComponentFixture<HeaderDrawerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HeaderDrawerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderDrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
