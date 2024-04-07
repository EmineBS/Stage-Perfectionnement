import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderDotsComponent } from './header-dots.component';

describe('HeaderDotsComponent', () => {
  let component: HeaderDotsComponent;
  let fixture: ComponentFixture<HeaderDotsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HeaderDotsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderDotsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
