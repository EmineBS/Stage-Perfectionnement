import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymPacksComponent } from './gym-packs.component';

describe('GymPacksComponent', () => {
  let component: GymPacksComponent;
  let fixture: ComponentFixture<GymPacksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GymPacksComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GymPacksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
