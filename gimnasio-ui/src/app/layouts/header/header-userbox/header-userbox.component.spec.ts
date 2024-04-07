import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderUserboxComponent } from './header-userbox.component';

describe('HeaderUserboxComponent', () => {
  let component: HeaderUserboxComponent;
  let fixture: ComponentFixture<HeaderUserboxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HeaderUserboxComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderUserboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
