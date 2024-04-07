import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavEventsComponent } from './nav-events.component';

describe('NavEventsComponent', () => {
  let component: NavEventsComponent;
  let fixture: ComponentFixture<NavEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavEventsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
