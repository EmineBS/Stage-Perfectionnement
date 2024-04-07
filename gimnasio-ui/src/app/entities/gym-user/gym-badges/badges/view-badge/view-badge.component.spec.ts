import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBadgeComponent } from './view-badge.component';

describe('ViewBadgeComponent', () => {
  let component: ViewBadgeComponent;
  let fixture: ComponentFixture<ViewBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewBadgeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViewBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
