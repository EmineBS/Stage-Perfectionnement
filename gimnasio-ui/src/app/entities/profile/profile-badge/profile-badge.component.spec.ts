import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileBadgeComponent } from './profile-badge.component';

describe('ViewBadgeComponent', () => {
  let component: ProfileBadgeComponent;
  let fixture: ComponentFixture<ProfileBadgeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileBadgeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfileBadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
