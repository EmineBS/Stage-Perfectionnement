import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BadgePackComponent } from './badge-pack.component';

describe('BadgePackComponent', () => {
  let component: BadgePackComponent;
  let fixture: ComponentFixture<BadgePackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BadgePackComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BadgePackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
