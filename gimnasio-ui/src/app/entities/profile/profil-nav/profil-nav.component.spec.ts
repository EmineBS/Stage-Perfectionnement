import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilNavComponent } from './profil-nav.component';

describe('ProfilNavComponent', () => {
  let component: ProfilNavComponent;
  let fixture: ComponentFixture<ProfilNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfilNavComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfilNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
